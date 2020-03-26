package com.example.googleAuth.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.googleAuth.model.UserInfo;
import com.example.googleAuth.service.SecurityService;
import com.example.googleAuth.service.UserService;

@RestController
@RequestMapping
public class RegistrationController {

	private UserService userService;

	@Autowired
	private SecurityService securityService;

	public RegistrationController(UserService userService) {
		this.userService = userService;

	}

	// Manual Registration
	@PostMapping(path = "/register")
	public String regsistration(@RequestBody UserInfo userInfo, HttpServletRequest request, Model model) {
		String password = userInfo.getPassword();
		UserInfo dbUser = userService.saveUserInfo(userInfo);
		securityService.autoLoginIn(dbUser.getEmail(), password, dbUser.getRole(), request);
		model.addAttribute("user", dbUser);

		// which user is loggging
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<? extends GrantedAuthority> grantedAuthority = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = grantedAuthority.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println(name);
		return "success";
	}
}
