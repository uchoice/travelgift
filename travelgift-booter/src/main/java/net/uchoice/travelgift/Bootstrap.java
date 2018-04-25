package net.uchoice.travelgift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@MapperScan("net.uchoice.travelgift.vote.dao")
//@EnableAutoConfiguration(exclude = { JpaRepositoriesAutoConfiguration.class// 禁止springboot自动加载持久化bean
//})
public class Bootstrap extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Bootstrap.class);
    }
}
