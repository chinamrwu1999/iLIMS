package com.amswh.iLIMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan({"com.amswh.iLIMS.mapper","com.amswh.iLIMS.generated.mapper"})
public class iLIMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(iLIMSApplication.class, args);
	}

}
