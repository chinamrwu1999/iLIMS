package com.amswh.iLIMS.service;


import com.amswh.iLIMS.domain.Disease;
import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.domain.Project;
import com.amswh.iLIMS.mapper.lims.IProduct;
import com.amswh.iLIMS.mapper.lims.IProject;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstantsService {
     @Resource
     ProductService productService;

    @Resource
    ProjectService projectService;

    @Resource
    DiseaseService diseaseService;

    private final Map<String,String> productMap=new HashMap<>();
    private final Map<String,String> projectMap=new HashMap<>();

    private final Map<String,String> product2Project=new HashMap<>();

    private final Map<String,String> diseaseMap=new HashMap<>();

    private final Map<String,String> project2DiseaseMap=new HashMap<>();

    private Map<String,String> analyte2ProductMap=new HashMap<>();


    @PostConstruct
    public void Init(){
         List<Project> projectList=this.projectService.list();
         for(Project obj:projectList){
             this.projectMap.put(obj.getCode(),obj.getName());
         }

        List<Product> productList=this.productService.list();
         for(Product obj:productList){
             this.productMap.put(obj.getCode(),obj.getName());
             this.product2Project.put(obj.getCode(),obj.getProjectCode());
         }

         List<Disease> diseaseList=diseaseService.list();
         for(Disease obj:diseaseList){
             this.diseaseMap.put(obj.getCode(),obj.getName());
         }

         List<Map<String,String>> projectDiseases=this.projectService.listProjectDiseases();

         for(Map<String,String> mp: projectDiseases){
             this.project2DiseaseMap.put(mp.get("projectCode"),mp.get("diseaseCode"));
         }
         this.analyte2ProductMap=this.productService.listAnalyte2Product();


   }

   public String getProjectName(String proejctCode){
        return this.projectMap.get(proejctCode);
   }

   public String getProductName(String productCode){
        return this.productMap.get(productCode);
   }

   public String getProjectNameByProductCode(String productCode){
        String projectCode=this.product2Project.get(productCode);
        return this.projectMap.get(projectCode);
   }

   public String getDiseaseByProductCode(String productCode){
         String  projectCode=this.product2Project.get(productCode);
         return this.diseaseMap.get(projectCode);
   }

   public String getProductIdBySampleHeader(String code){
        return this.analyte2ProductMap.get(code);
   }

}
