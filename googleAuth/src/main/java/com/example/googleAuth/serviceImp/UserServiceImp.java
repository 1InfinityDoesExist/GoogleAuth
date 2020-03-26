package com.example.googleAuth.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.googleAuth.model.UserInfo;
import com.example.googleAuth.repository.UserInfoRepository;
import com.example.googleAuth.service.UserService;
import com.example.googleAuth.utility.PasswordUtil;

@Service
@Transactional

public class UserServiceImp implements UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfo saveUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfo.setEnabled(true);
		userInfo.setRole("ADMIN");
		if (StringUtils.hasText(userInfo.getPassword())) {
			userInfo.setPassword(PasswordUtil.getEncodedPassword(userInfo.getPassword()));
		}
		return userInfoRepository.save(userInfo);
	}

	@Override
	public UserInfo findByEmail(String accountEmail) {
		// TODO Auto-generated method stub
		return userInfoRepository.findByEmail(accountEmail);
	}

	@Override
	public void update(UserInfo dbUser) {
		// TODO Auto-generated method stub
		userInfoRepository.save(dbUser);

	}

}
