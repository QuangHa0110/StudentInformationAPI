package com.manageuniversity.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manageuniversity.entity.Classes;
import com.manageuniversity.service.ClassesService;
import com.manageuniversity.service.UsersService;

@RunWith(SpringRunner.class)
@WebMvcTest(ClassesController.class)
public class ClassesControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ClassesService classesService;

	@MockBean
	private UsersService usersService;
	
	@Test
	public void testFindAllClasses() throws Exception {
		List<Classes> list = new ArrayList<>();
		list.add(classesService.findById(21).getBody());
		
	
		
		when(classesService.findAll()).thenReturn(list);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/classes/").header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjI0ODk5NjAwfQ.JvnMOVyARBhD33KAEIVwLp1B4_brwbnp21-sW5oh2KBABdKFiem56EfM9EfI2Gp9h3V2Uo_0NiPOHhB3EQxeJQ"))
		
				.andExpect(status().isOk()).andExpect((ResultMatcher) content().json("[{}]"));
		
	}
}
