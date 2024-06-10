package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.Sequence;
import com.amswh.iLIMS.mapper.lims.ISequence;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class SeqService extends ServiceImpl<ISequence, Sequence> {


    private  final String tableName="sequence";
    private  String nameColName="seqName";
    private  String idColName="seqId";
    private final ConcurrentMap<String, SequenceBank> sequences = new ConcurrentHashMap<>();


    public Long getNextSeqId(String seqName, long staggerMax) {
        SequenceBank bank = this.getBank(seqName);
        return bank.getNextSeqId(staggerMax);
    }

    /**
     * Force bank refresh.
     * @param seqName    the seq name
     * @param staggerMax the stagger max
     */
    public void forceBankRefresh(String seqName, long staggerMax) {
        // don't use the get method because we don't want to create if it fails
        SequenceBank bank = sequences.get(seqName);
        if (bank == null) {
            return;
        }

        bank.refresh(staggerMax);
    }

    private SequenceBank getBank(String seqName) {
        SequenceBank bank = sequences.get(seqName);

        if (bank == null) {
            long bankSize = SequenceBank.DEF_BANK_SIZE;
            bank = new SequenceBank(seqName, bankSize);
            SequenceBank bankFromCache = sequences.putIfAbsent(seqName, bank);
            bank = bankFromCache != null ? bankFromCache : bank;
        }
        return bank;
    }




    private int updateForLock() {
        return this.baseMapper.updateForLock(nameColName);
    }

    private int updateSeq(String seqName,Long latestId){
        return  this.baseMapper.updateSequence(seqName,latestId);
    }


    private final class SequenceBank {
        public static final long DEF_BANK_SIZE = 35;
        public static final long MAX_BANK_SIZE = 5000;
        public static final long START_SEQ_ID = 10000;

        private final String seqName;
        private final long bankSize;


        private long curSeqId;
        private long maxSeqId;

        private SequenceBank(String seqName, long bankSize) {
            this.seqName = seqName;
            curSeqId = 0;
            maxSeqId = 0;
            this.bankSize = bankSize;
        }

        private Long getNextSeqId(long staggerMax) {
            long stagger = 1;
            if (staggerMax > 1) {
                stagger = (long) Math.ceil(Math.random() * staggerMax);
                if (stagger == 0) stagger = 1;
            }
            synchronized (this) {
                if ((curSeqId + stagger) <= maxSeqId) {
                    long retSeqId = curSeqId;
                    curSeqId += stagger;
                    return retSeqId;
                } else {
                    try {
                        fillBank(stagger);
                        if ((curSeqId + stagger) <= maxSeqId) {
                            long retSeqId = curSeqId;
                            curSeqId += stagger;
                            return retSeqId;
                        } else {
                            return null;
                        }
                    }catch (Exception err){
                        err.printStackTrace();
                        return null;
                    }
                }
            }
        }

        private synchronized void refresh(long staggerMax) {
            this.curSeqId = this.maxSeqId;
            try {
                this.fillBank(staggerMax);
            }catch (Exception err){
                err.printStackTrace();
            }
        }

        /*
           The algorithm to get the new sequence id in a thread safe way is the following:
           1 - run an update with no changes to get a lock on the record
               1bis - if no record is found, try to create and update it to get the lock
           2 - select the record (now locked) to get the curSeqId
           3 - increment the sequence
           The three steps are executed in one dedicated database transaction.
         */
        // @Transactional(propagation = Propagation.REQUIRES_NEW )
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        private void fillBank(long stagger) throws  Exception {
            // no need to get a new bank, SeqIds available
            if ((curSeqId + stagger) <= maxSeqId) {
                return;
            }
            long bankSize = this.bankSize;
            if (stagger > 1) {
                   bankSize = stagger * DEF_BANK_SIZE;
            }
            if (bankSize > MAX_BANK_SIZE) {
                bankSize = MAX_BANK_SIZE;
            }
            try {
                    // 1 - run an update with no changes to get a lock on the record
                    Sequence seq=new Sequence();
                    seq.setSeqName(seqName);
                    QueryWrapper<Sequence> queryWrapper=new QueryWrapper<>(seq);
                    if (SeqService.this.updateForLock() <= 0) {
                        Sequence one= SeqService.this.getOne(queryWrapper);
                        if(one==null) {
                              seq.setSeqName(this.seqName);
                              seq.setSeqId(START_SEQ_ID);
                            if (!SeqService.this.save(seq)) {
                                throw new Exception("creating new sequence failed when filling sequence bank '" + seqName + "'");
                            }
                        }
                    }
                    // 2 - select the record (now locked) to get the curSeqId
                    Sequence one= SeqService.this.getOne(queryWrapper);
                    if (one!=null) {
                        curSeqId=one.getSeqId();
                    }else{
                        throw new Exception("Failed to find the sequence record for sequence: " + seqName);
                    }
                    // 3 - increment the sequence

                   int k= SeqService.this.updateSeq(seqName,curSeqId+bankSize);
                    if (k<=0) {
                         throw new Exception("Update failed, no rows changes for seqName: " + seqName);
                     }
                } catch (SQLException sqle) {
                    throw sqle;
                }
                  maxSeqId = curSeqId + bankSize;

        }
    }




}
