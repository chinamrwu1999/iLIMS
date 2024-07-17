package com.amswh.iLIMS.project.controller;
/*

 */



import com.amswh.iLIMS.project.service.PartyBarService;
import com.amswh.iLIMS.project.service.PartyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    PartyService partyService;

    @Resource
    PartyBarService partyBarService;







}
