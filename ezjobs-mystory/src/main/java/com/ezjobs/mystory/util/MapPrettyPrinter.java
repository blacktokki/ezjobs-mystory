package com.ezjobs.mystory.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class MapPrettyPrinter{

	@Autowired
	ObjectMapper objectMapper;
	
	public String print(Map<String,Object> map,String... keys) throws Exception {
		objectMapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		final ObjectWriter writer=objectMapper.writerWithDefaultPrettyPrinter();
		String str="";
		for(String key:keys) {
			str+="\n"+key+" : "+writer.writeValueAsString(map.get(key));
		}
		return str;
	}
	
	public void mavInfo(ModelAndView mav,Class<?> clazz,String... attributes) throws Exception {
		Logger logger = LoggerFactory.getLogger(clazz);
		Map<String, Object> map=mav.getModel();
    	logger.info(print(map,attributes));
	}
}
