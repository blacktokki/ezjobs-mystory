package com.ezjobs.mystory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
//import static org.mockito.BDDMockito.given;

import com.ezjobs.mystory.controller.BoardController;
import com.ezjobs.mystory.service.board.BoardService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BoardController.class, 
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration = SecurityAutoConfiguration.class
		)

public class BoardControllerTest {
	

	private static final Logger logger = LoggerFactory.getLogger(BoardControllerTest.class);
	
    @Inject
    private MockMvc mvc;

    @MockBean
    BoardService service;
    
    @Test
    public void test1() throws Exception {
        //given
        //final BoardImpl board = new BoardImpl();
    	//given(this.service.community(model)).willReturn(board);
        //when
        final ResultActions actions = mvc.perform(get("/board/community"))
        		.andDo(print());
        //then
        actions.andExpect(status().isOk());
        logger.info(actions.andReturn().getResponse().toString());

    }
}