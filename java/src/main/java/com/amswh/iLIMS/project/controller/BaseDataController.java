package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.service.ConstantsService;
import com.amswh.iLIMS.project.service.PartygroupService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BaseDataController {

    @Resource
    PartygroupService partygroupService;



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
     * 新增部门
     * @return
     */
    @PostMapping("/department/create")
    public AjaxResult createDepartment(@RequestBody Map<String,Object> input){

        return null;
    }

    @GetMapping("/list/partners")
    public AjaxResult listPartners(){

        return  null;
    }

    /**
     * 新增Partner
     * @return
     */
    @PostMapping("/partner/create")
    public AjaxResult createPartner(@RequestBody Map<String,Object> input){

        return null;
    }
}
