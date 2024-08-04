package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Sequence;
import com.amswh.iLIMS.project.mapper.lims.ISequence;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class SeqService extends ServiceImpl<ISequence, Sequence> {

   @Resource
    JdbcClient jdbcClient;

    private  final String tableName="sequence";
    private  String nameColName="seqName";
    private  String idColName="seqId";
    private final ConcurrentMap<String, SequenceBank> sequences = new ConcurrentHashMap<>();
    private  final DateTimeFormatter date6Formatter = DateTimeFormatter.ofPattern("yyMMdd");



    public String nextPartySeq(){
        String partyId=String.format("%08d",getNextSeqId("party", 1L));
        return partyId;
    }

    public String nextBarId(){
        String barId=String.format("B%010d",getNextSeqId("PartnerBar",1L));
        return barId;
    }



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
            bank = new SequenceBank(seqName,  SequenceBank.DEF_BANK_SIZE);
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
        public static final long DEF_BANK_SIZE = 100;
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
                        Sequence one= SeqService.this.getById(seqName);
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

    /**
     * 实验计划序列号
     * @return
     */
    public String nextExpPlainSeq(){
        long seqId=this.getNextSeqId("expPlain",1L);
         return String.format("%s%03d",LocalDateTime.now().format(date6Formatter),seqId);

    }

    @PostConstruct
    public void Initialize(){
        List<Map<String,Object>> rows=this.jdbcClient.sql("SELECT seqName,colName FROM Sequence").query().listOfRows();
        String sql="SELECT max(substr(%s,2,11)) FROM %s";
        String updateSQL="UPDATE sequence set seqId=:seqId WHERE seqName=:seqName";
        for(Map<String,Object> row:rows){
            String strSQL=String.format(sql,row.get("colName"),row.get("seqName"));
            Object obj=jdbcClient.sql(strSQL).query().singleValue();
            Long maxId=0L;
            if(obj!=null){
                maxId=Long.parseLong(obj.toString());
            }
            jdbcClient.sql(updateSQL).param("seqId",maxId+1)
                    .param("seqName",row.get("seqName").toString()).update();

        }
        System.out.println("sequence service initialized ");
    }
//
//
//    public Long getMaxUsedSeq(String seqId){
//
//    }


}
