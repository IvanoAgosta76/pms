package com.fullstack.devops.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fullstack.devops.DevopsApplicationTests;


class UserControllerTests extends DevopsApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testGetAllUsers() throws Exception {
		mockMvc.perform(get("/pms/users"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/list-users"));
	}

	@Test
	void testDeleteAllUsers() throws Exception {
		mockMvc.perform(get("/pms/users"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("user/list-users"));
	}
	
	@Test
	void testEditUserById() throws Exception {
		mockMvc.perform(get("/pms/user/update/{id}", 1)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("user/update-user"));
	}
	
}
