package com.amswh.MYLIMS;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.amswh.MYLIMS.mapper")
public class MylimsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MylimsApplication.class, args);
	}

}
