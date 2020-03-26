package com.example.googleAuth.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.google.api.plus.Person;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.googleAuth.model.UserInfo;
import com.example.googleAuth.service.GoogleService;
import com.example.googleAuth.service.SecurityService;
import com.example.googleAuth.service.UserService;

@RestController
@RequestMapping
public class SpringGoolgeController {

	private static final Logger logger = LoggerFactory.getLogger(SpringGoolgeController.class);

	@Autowired
	private GoogleService googleService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;

	@GetMapping(path = "/googlelogin")
	public Principal googleLogin(Principal principal) {
		RedirectView redirectView = new RedirectView();
		String url = googleService.googleLogin();
		logger.info("Url " + url);
		redirectView.setUrl(url); // you can return redirectView as well
		return principal;
	}

	@GetMapping(path = "/google")
	public String google(@RequestParam("code") String code) {
		String accessToken = googleService.getGoogleAccessToken(code);
		return "redirect:/googleprofiledata/" + accessToken;
	}

	@GetMapping(path = "/googleprofiledata/{accessToken:.+}")
	public String googleprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request) {
		Person user = googleService.getGoogleUserProfile(accessToken);

		// update if first logged in manually ... after that with social
		UserInfo dbUser = userService.findByEmail(user.getAccountEmail());
		String role = "USER";
		if (dbUser != null) {
			dbUser.setFirstName(user.getDisplayName());
			dbUser.setLastName(user.getFamilyName());
			dbUser.setImageUrl(user.getImageUrl());
			userService.update(dbUser);
			role = dbUser.getRole();
			model.addAttribute("user", dbUser);
		} else {
			UserInfo userInfo = new UserInfo(user.getGivenName(), user.getFamilyName(), user.getAccountEmail());
			userInfo.setEmail(user.getAccountEmail());
			userInfo.setEnabled(true);
			userInfo.setRole("USER");
			userService.saveUserInfo(userInfo);
			model.addAttribute("user", userInfo);
		}

		securityService.autoLoginIn(user.getAccountEmail(), null, role, request);
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println(name);

		// UserInfo userInfo = new UserInfo(user.getGivenName(),
		// user.getFamilyName(), user.getImageUrl());
		// model.addAttribute("user", userInfo);
		return "success";
	}

}
