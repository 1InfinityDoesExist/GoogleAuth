package com.example.googleAuth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GoolgeAuthController {

	@RequestMapping(path = "/goauth")
	public Principal user(Principal principal) {
		return principal;

	}

}
