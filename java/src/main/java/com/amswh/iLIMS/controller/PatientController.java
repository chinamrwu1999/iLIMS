package com.amswh.iLIMS.controller;
/*

 */



import com.amswh.iLIMS.domain.PartyBar;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.PartyBarService;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.service.PersonService;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    PartyService partyService;

    @Resource
    PartyBarService partyBarService;







}
