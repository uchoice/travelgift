package net.uchoice.travelgift.vote.conf;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.uchoice.travelgift.vote.vo.ContentItemVo;

@Configuration
public class AppConfiguration {

	@Bean
	public ObjectMapper ObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ContentItemVo.class);
		return objectMapper;
	}

}
