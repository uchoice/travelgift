package net.uchoice.travelgift;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication
@MapperScan("net.uchoice.travelgift.vote.dao")
@EnableAutoConfiguration(exclude = { JpaRepositoriesAutoConfiguration.class// 禁止springboot自动加载持久化bean
})
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

}
