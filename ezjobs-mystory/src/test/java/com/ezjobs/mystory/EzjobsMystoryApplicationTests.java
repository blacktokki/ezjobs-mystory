package com.ezjobs.mystory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class EzjobsMystoryApplicationTests {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardControllerTest.class);
	
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private String mapPrettyPrint(Map<String,Object> map,String... keys) throws Exception {
		objectMapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		final ObjectWriter writer=objectMapper.writerWithDefaultPrettyPrinter();
		String str="";
		for(String key:keys) {
			str+="\n"+key+" : "+writer.writeValueAsString(map.get(key));
		}
		return str;
	}
	
	private void modelPrettyprint(ResultActions actions,String... attributes) throws Exception {
		Map<String, Object> map=actions.andReturn().getModelAndView().getModel();
    	logger.info(mapPrettyPrint(map,attributes));
	}
	
    @Test
    public void community() throws Exception {
    	final String[] modelAttributes= {"boards"};
    	final ResultActions actions =mockMvc.perform(get("/board/community"))
    			.andExpect(status().isOk())
    			.andDo(print())
    			.andExpect(model().attributeExists(modelAttributes));
    	modelPrettyprint(actions,modelAttributes);
    }

}
