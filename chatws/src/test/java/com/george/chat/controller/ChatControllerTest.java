package com.george.chat.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=ChatController.class)
public class ChatControllerTest{
	
	@Autowired
	private ChatController chatController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
	}
	
	@Test
	public void testChatHandler() throws Exception{
		mockMvc.perform(get("/").param("username", ChatController.DEFAULT_USERNAME))
			   .andExpect(handler().handlerType(ChatController.class))
			   .andExpect(handler().methodName("index"));
	}
	
	@Test
	public void testChatAccess() throws Exception{
		mockMvc.perform(get("/").param("username", ChatController.DEFAULT_USERNAME))
			   .andExpect(model().attribute("username", ChatController.DEFAULT_USERNAME))
			   .andExpect(status().isOk())
			   .andExpect(view().name("chat"));
	}
	
}