package com.ezjobs.mystory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.TagRepository;
import com.ezjobs.mystory.util.MapPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class ResumeTest {
		
	static final Logger logger = LoggerFactory.getLogger(ResumeTest.class);
	
	@Autowired
	MockHttpSession loginSession;
	
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MapPrettyPrinter printer;
	
    @Test
    public void resume() throws Exception {
    	final String[] modelAttributes= {"resumes"};
    	final ResultActions actions =mockMvc
    			.perform(get("/resume").session(loginSession))
    			.andExpect(status().isOk())
    			.andDo(print())
    			.andExpect(model().attributeExists(modelAttributes));
    	printer.mavInfo(actions.andReturn().getModelAndView(),ResumeTest.class,modelAttributes);
    	
    }
    
    @Test
    public void content() throws Exception {
    	final String[] modelAttributes= {"resumes"};
    	//final ResultActions actions =
    	mockMvc
    			.perform(get("/content").session(loginSession))
    			.andExpect(status().isOk())
    			.andDo(print())
    			.andExpect(model().attributeExists(modelAttributes));
    	//printer.mavInfo(actions.andReturn().getModelAndView(),ResumeTest.class,modelAttributes);
    	
    }
    
    @Autowired
    private ResumeRepository resumeRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    
    @Test
    public void save() throws Exception {
    	Tag tag=new Tag();
    	tag.setName("test");
    	tag.setType("키워드2");
    	Tag tag2=new Tag();
    	tag2.setName("testtest");
    	tag2.setType("키워드2");
    	//////////////////////////////////////
    	List<Tag>tags=Lists.list(tag,tag2);
    	List<String>tagsName=Lists.newArrayList();
    	tags.forEach(t -> tagsName.add(t.getName()));
    	tagRepository.saveAll(tags);
    	Set<Tag> tagsSet=tagRepository.findByNames(tagsName);
    	System.out.println(tagsSet.toString());
    	
    	Resume resume = new Resume();
    	resume.setQuestion("test01");
    	resume.setUserId("ydh051541");
    	tagsSet.forEach(t->t.getResumes().add(resume));
    	resume.setTags(tagsSet);
    	resumeRepository.save(resume);
    	System.out.println("-----");
    	resume.getTags().clear();
    	resumeRepository.save(resume);
    	System.out.println(resume.toString());
    	
    }

}
