package com.ezjobs.mystory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(TestConfig.class)

public class JpaTest {
	

	private static final Logger logger = LoggerFactory.getLogger(JpaTest.class);
	
	@Autowired
    private TagRepository tagRepository;
    
	@Autowired
    private ResumeRepository resumeRepository;

	@Test
    public void tag1() throws Exception {
    	Page<Tag> tags=tagRepository.findAll(PageRequest.of(0, 25));
    	assertThat(tags, is(notNullValue()));
    	tags.getContent().forEach(tag->{
    		logger.info(tag.toString());
    		logger.info(tag.getResumes().toString());
    	});
    }
    
    @Test
    public void resume1() throws Exception {
    	Page<Resume> resumes=resumeRepository.findAllByIdGreaterThan(0,PageRequest.of(0, 5));
    	assertThat(resumes, is(notNullValue()));
    	resumes.getContent().forEach(resume->{
    		logger.info(resume.toString());
    		logger.info(resume.getTags().toString());
    	});
    }
	
}