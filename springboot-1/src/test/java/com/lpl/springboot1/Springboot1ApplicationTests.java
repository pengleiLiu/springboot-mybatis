package com.lpl.springboot1;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.lpl.springboot1.controller.HelloController;
import com.lpl.springboot1.pojo.Student;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot1ApplicationTests {
	
	@Autowired
    private WebApplicationContext context;
	
	private MockMvc  mvc;
	
	@Before		//这个方法在每个方法执行之前都会执行一遍
	public void setUp() throws Exception{
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
	}

	@Test
	public void contextLoads() throws Exception {
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(status().isOk())
						.andExpect(content().string(equalTo("Hello World 斤斤计较")))
						.andDo(MockMvcResultHandlers.print()).andReturn();
	
		//Assert.assertEquals(expected, actual);
		//Assert.assertEquals(result.getModelAndView().getViewName(),"hello word");
		//System.out.println(result);
		
	}
	
	@Test
	public void testRedisCache() throws Exception{
			
		MvcResult andReturn = mvc.perform(MockMvcRequestBuilders.post("/updateStudent.do")
				.param("id", "2").param("name", "刘大磊").param("age","15").param("sex","0")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	public void insert() throws Exception{
			
		MvcResult andReturn = mvc.perform(MockMvcRequestBuilders.post("/insert.do")
				.param("id", "10").param("name", "刘朋磊").param("age","15").param("sex","0")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

}
