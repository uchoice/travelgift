package net.uchoice.travelgift.vote.conf;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.uchoice.travelgift.vote.vo.ContentItemVo;

@Configuration
public class AppConfiguration {

	@Bean
	public ObjectMapper ObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ContentItemVo.class);
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
		});
		return objectMapper;
	}

}
