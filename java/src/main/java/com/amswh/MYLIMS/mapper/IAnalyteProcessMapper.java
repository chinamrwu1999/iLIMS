package com.amswh.MYLIMS.mapper;

import com.amswh.MYLIMS.domain.AnalyteProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAnalyteProcessMapper extends BaseMapper<AnalyteProcess> {


    /*
         等待一审样本列表
     */
    @Select("SELECT predict.analyteCode,predict.createTime,name,case gender WHEN 'F' THEN '女' WHEN 'M' THEN '男' ELSE NULL END as gender,productCode FROM  "+
            "(SELECT analyteCode,createTime FROM AnalytePredict  WHERE createTime > (SELECT max(createTime) FROM AnalytePredict) - INTERVAL 7 DAY ) AS predict LEFT JOIN "+
            "(SELECT analyteCode FROM AnalyteProcess WHERE action='AUDIT1' AND createTime > (SELECT max(createTime) FROM AnalyteProcess) - INTERVAL 10 DAY ) AS process  "+
            "ON predict.analyteCode=process.analyteCode "+
            "LEFT JOIN analyte ON analyte.analyteCode=predict.analyteCode "+
            "LEFT JOIN Patient ON analyte.barCode=Patient.barCode "+
            "LEFT JOIN BioSample ON analyte.barCode=BioSample.barCode AND BioSample.barCode=Patient.barCode "+
            "WHERE process.analyteCode IS NULL ORDER BY predict.createTime"
    )
    public List<Map<String,Object>> analytesToAudit1(String action);

    /*
           等待二审样本列表
       */
    @Select("SELECT predict.analyteCode,predict.createTime,name,case gender WHEN 'F' THEN '女' WHEN 'M' THEN '男' ELSE NULL END as gender,productCode FROM  "+
            "(SELECT analyteCode,createTime FROM analyteProcess WHERE  action='AUDIT1' createTime > (SELECT max(createTime) FROM AnalytePredict) - INTERVAL 7 DAY ) AS P1 LEFT JOIN "+
            "(SELECT analyteCode FROM analyteProcess WHERE action='AUDIT2' AND createTime > (SELECT max(createTime) FROM AnalyteProcess) - INTERVAL 7 DAY ) AS P2  "+
            "ON P1.analyteCode=P2.analyteCode "+
            "LEFT JOIN analyte   ON analyte.analyteCode=P1.analyteCode "+
            "LEFT JOIN Patient   ON analyte.barCode=Patient.barCode "+
            "LEFT JOIN BioSample ON analyte.barCode=BioSample.barCode AND BioSample.barCode=Patient.barCode "+
            "WHERE P2.analyteCode IS NULL ORDER BY P1.createTime"
    )
    public List<Map<String,Object>> analytesToAudit2(String review);


    /*
       等待二审样本列表
   */
    @Select("SELECT predict.analyteCode,predict.createTime,name,case gender WHEN 'F' THEN '女' WHEN 'M' THEN '男' ELSE NULL END as gender,productCode FROM  "+
            "(SELECT analyteCode,createTime FROM analyteProcess WHERE  action='AUDIT2' createTime > (SELECT max(createTime) FROM AnalytePredict) - INTERVAL 7 DAY ) AS P1 LEFT JOIN "+
            "(SELECT analyteCode FROM analyteProcess WHERE action='AUDIT3' AND createTime > (SELECT max(createTime) FROM AnalyteProcess) - INTERVAL 7 DAY ) AS P2  "+
            "ON P1.analyteCode=P2.analyteCode "+
            "LEFT JOIN analyte   ON analyte.analyteCode=P1.analyteCode "+
            "LEFT JOIN Patient   ON analyte.barCode=Patient.barCode "+
            "LEFT JOIN BioSample ON analyte.barCode=BioSample.barCode AND BioSample.barCode=Patient.barCode "+
            "WHERE P2.analyteCode IS NULL ORDER BY P1.createTime"
    )
    public List<Map<String,Object>> analytesToAudit3(String review);



    /*
      某个分析物的状态跟踪信息
 */
    @Select("SELECT A.barCode 条码号,A.analyteCode 分析物代码,BS.createTime 收样时间, " +
            "APR.testTime 检测时间,APR.predict 检测结果,AP1.createTime 审核日期,AP2.createTime 报告日期 "+
            "FROM analyte A " +
            "FROM BioSample BS ON BS.barCode=A.barCode "+
            "LEFT JOIN analytePredict APR ON A.analyteCode=PR.analyteCode "+
            "LEFT JOIN analyteProcess AP1 ON A.analyteCode=AP1.analyteCode "+
            "LEFT JOIN analyteProcess AP2 ON A.analyteCode=AP2.analyteCode "+
            "WHERE A.barCode=#{barCode} "+
            "AND AP1.action='AUDIT1' "+
            "AND AP2.action='PUBLISH' "+
            "WHERE A.analyteCode=#{analyteCode} "+
            "ORDER BY A.analyteCode"
    )
    public List<Map<String,Object>> analyteStatusTrace(String analyteCode);



}
