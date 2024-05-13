package com.amswh.iLIMS.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.mapper.lims.IPerson;
import org.springframework.stereotype.Service;


@Service
public class PersonService extends ServiceImpl<IPerson, Person> {
	}