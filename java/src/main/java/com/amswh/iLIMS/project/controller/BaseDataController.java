package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.service.ConstantsService;
import com.amswh.iLIMS.project.service.PartyService;
import com.amswh.iLIMS.project.service.PartygroupService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BaseDataController {

    @Resource
    PartygroupService partygroupService;

  @Resource
    PartyService partyService;

    @Resource
    ConstantsService constantsService;

    @GetMapping("/list/products")
    public AjaxResult listProducts(){

        return  AjaxResult.success(constantsService.listProducts());
    }

    /**
     * 新增产品
     * @return
     */
    @PostMapping("/product/create")
    public AjaxResult createProduct(@RequestBody Map<String,Object> input){

        return null;
    }

    @GetMapping("/list/projects")
    public AjaxResult listProjects(){

         return  AjaxResult.success(constantsService.listProjects());
    }

    /**
     * 新增产品
     * @return
     */
    @PostMapping("/project/create")
    public AjaxResult createProject(@RequestBody Map<String,Object> input){

        return null;
    }
    @GetMapping("/list/departments")
    public AjaxResult listDepartments(){

        return  AjaxResult.success(partygroupService.listDepartments());
    }

    /**
     * 列出部门人员
     * @param deptId
     * @return
     */
    @RequestMapping("/list/deptEmployee/{deptId}")
    public AjaxResult listDepartmentEmployees(@PathVariable String deptId){

        return  AjaxResult.success(partyService.listDepartentEmployees(deptId));
    }

    /**
     * 新增部门
     * @return
     */
    @PostMapping("/department/create")
    public AjaxResult createDepartment(@RequestBody Map<String,Object> input){

        return null;
    }

    @GetMapping("/list/partners")
    public AjaxResult listPartners(){
        return  AjaxResult.success(partygroupService.listPartners());

    }

    /**
     * 全部员工列表
     * @return
     */
    @RequestMapping ("/list/employees")
    public AjaxResult listEmployees(){

        return AjaxResult.success(partyService.listAllEmployees());
    }

    /**
     * 新增Partner
     * @return
     */
    @PostMapping("/partner/create")
    public AjaxResult createPartner(@RequestBody Map<String,Object> input){

        return null;
    }

    @GetMapping("/list/enums/{type}")
    public AjaxResult listEnums(@PathVariable String type){
        return  AjaxResult.success(constantsService.fetchEnums(type));

    }

    /**
     *
     * @param parentCode
     * @return
     */
    @GetMapping("/getChildCities/{parentCode}")
    public AjaxResult getChildCities(String parentCode){
         return AjaxResult.success(constantsService.getChildrenCities(parentCode));
    }

}
