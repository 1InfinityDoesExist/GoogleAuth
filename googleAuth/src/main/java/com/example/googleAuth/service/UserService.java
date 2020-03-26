package com.example.googleAuth.service;

import org.springframework.stereotype.Service;

import com.example.googleAuth.model.UserInfo;

@Service
public interface UserService {

	public UserInfo saveUserInfo(UserInfo userInfo);

	public UserInfo findByEmail(String accountEmail);

	public void update(UserInfo dbUser);

}
