package com.example.googleAuth.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public interface SecurityService {

	public void autoLoginIn(String email, String password, String role, HttpServletRequest request);

}
