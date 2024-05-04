package com.amswh.MYLIMS.service;

import com.amswh.MYLIMS.domain.Patient;
import com.amswh.MYLIMS.mapper.IPatientMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends ServiceImpl<IPatientMapper, Patient> {
}
