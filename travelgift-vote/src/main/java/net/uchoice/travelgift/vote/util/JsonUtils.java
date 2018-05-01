package net.uchoice.travelgift.vote.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.uchoice.travelgift.vote.vo.ContentItemVo;

public class JsonUtils {
	
	public static final ObjectMapper OM = new ObjectMapper();
	
	static {
		OM.getTypeFactory().constructParametricType(ArrayList.class, ContentItemVo.class);
	}
	
	public static JavaType getCollectionType(@SuppressWarnings("rawtypes") Class<? extends Collection> collectionClass, Class<?> elementClass) {
		return OM.getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}
	
	public static <E> E parse(String s, Class<E> clazz) {
		try {
			return OM.readValue(s, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <E> E parse(String s, JavaType clazz) {
		try {
			return OM.readValue(s, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
