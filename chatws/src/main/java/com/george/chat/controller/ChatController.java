package com.george.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController{
	
	public static final String DEFAULT_USERNAME = "user"; 
	
	@RequestMapping("/")
	public ModelAndView index(){
		return new ModelAndView("chat","username",DEFAULT_USERNAME);
	}
	
}