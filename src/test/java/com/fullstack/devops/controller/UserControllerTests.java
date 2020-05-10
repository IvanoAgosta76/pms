package com.fullstack.devops.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.devops.DevopsApplicationTests;
import com.fullstack.devops.model.User;
import com.fullstack.devops.repository.UserRepository;


class UserControllerTests extends DevopsApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Mock
	private UserRepository userRepository;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testGetAllUsers() throws Exception {
		mockMvc.perform(get("/pms/users"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("user/list-users"));
	}
	
	@Test
	void testAddUser() throws Exception {
		mockMvc.perform(get("/pms/user/add"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("user/add-user"));
	}
	
	@Test
	void testCreateUser() throws Exception {
		User user = new User();
		user.setFirstname("testname");
		user.setLastname("testsurname");
		user.setEmail("x.x@x.com");
		
		mockMvc.perform(post("/pms/user/create")
				.contentType(MediaType.ALL)
				.flashAttr("user", user))
		.andDo(print())
		.andExpect(redirectedUrl("/pms/users"));
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
	
	@Test
	void testDeleteUserById() throws Exception {
		
		List<User> users = new ArrayList<User>();
				
		userRepository.findAll().forEach(users::add);
		
		if(users.isEmpty()) {
			mockMvc.perform(get("/pms/user/delete/{id}", 0)
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("user/list-users"));
		} else {
			mockMvc.perform(get("/pms/user/delete/{id}", users.stream().filter(u -> u.getEmail().equals("x.x@x.com")))
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(redirectedUrl("/pms/users"));	
		}
	}
	
}
