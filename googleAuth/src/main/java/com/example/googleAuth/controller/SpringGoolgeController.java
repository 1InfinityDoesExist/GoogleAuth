package com.example.googleAuth.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.googleAuth.model.ApiResponse;
import com.example.googleAuth.model.UserInfo;
import com.example.googleAuth.service.GoogleService;
import com.example.googleAuth.service.SecurityService;
import com.example.googleAuth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
@Api(value = "", description = "")
public class SpringGoolgeController {

	private static final Logger logger = LoggerFactory.getLogger(SpringGoolgeController.class);

	private GoogleService googleService;
	private SecurityService securityService;
	private UserService userService;

	public SpringGoolgeController(GoogleService googleService, SecurityService securityService,
			UserService userService) {
		this.googleService = googleService;
		this.securityService = securityService;
		this.userService = userService;
	}

	// @GetMapping(path = "/googlelogin")
	// @ApiOperation(value = "/googlelogin", notes = "User Login Using Social
	// Service , Google Account")
	// public Principal googleLogin(Principal principal) {
	// logger.info("Inside googleLogin method, To redirect user to google login
	// page");
	// RedirectView redirectView = new RedirectView();
	// String url = googleService.googleLogin(); // get authenticated google
	// // URL
	// logger.info("Url--------------------> " + url);
	// redirectView.setUrl(url); // you can return redirectView as well
	// return principal;
	// }

	@GetMapping(path = "/googlelogin")
	@ApiOperation(value = "/googlelogin", notes = "User Login Using Social Service , Google Account")
	public RedirectView googleLogin() {
		logger.info("Inside googleLogin method, To redirect user to google login page");
		RedirectView redirectView = new RedirectView();
		String url = googleService.googleLogin(); // get authenticated google
													// URL
		logger.info("Url--------------------> " + url);
		redirectView.setUrl(url); // you can return redirectView as well
		logger.info("RedirectView " + redirectView);
		return redirectView;
	}

	@GetMapping(path = "/googlelogin/{tenantId}")
	@ApiOperation(value = "/googlelogin", notes = "User Login Using Social Service , Google Account")
	public ApiResponse googleLogin(Principal principal,
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		logger.info("Redirecting tenant------" + tenantId + "---------- To Google Login Page");
		RedirectView redirectView = new RedirectView();
		String url = googleService.googleLogin(); // get authenticated google
													// URL
		redirectView.setUrl(url); // you can return redirectView as well
		return new ApiResponse(200, "successfully Redirected To GoogleAuth", url);
	}

	@GetMapping(path = "/login/oauth2/code/google")
	public String google(@RequestParam("code") String code) {
		logger.info("**************Inside the get mapping of /google*******************");
		String accessToken = googleService.getGoogleAccessToken(code);
		logger.info("Access Token *************************" + accessToken);
		return "redirect:/googleprofiledata/" + accessToken;
	}

	@GetMapping(path = "/googleprofiledata/{accessToken:.+}")
	public String googleprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request) {
		logger.info("Inside googleProfileDate method of SpingGoogleController class");
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
