package cn.wenda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.wenda.dao")
public class WendaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WendaApplication.class, args);
	}

}
