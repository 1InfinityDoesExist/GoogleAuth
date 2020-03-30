package com.example.googleAuth.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.googleAuth.controllerInterface.TenantGoogleAuthControllerInterface;
import com.example.googleAuth.model.ApiResponse;
import com.example.googleAuth.model.TenantGoogleAuthDetails;
import com.example.googleAuth.service.TenantGoogleAuthService;

@RestController
@RequestMapping(path = "/googleauth")
public class TenantGoogleAuthController implements TenantGoogleAuthControllerInterface {

	private static final Logger logger = LoggerFactory.getLogger(TenantGoogleAuthController.class);

	@Autowired
	private TenantGoogleAuthService tenantGoogleAuthService;

	@Override
	public ApiResponse saveTenantGoogleAuthDetails(@Valid TenantGoogleAuthDetails tenantGoogleAuthDetails,
			String tenantid) {
		// TODO Auto-generated method stub
		TenantGoogleAuthDetails tenantGoogleAuthResponse = tenantGoogleAuthService
				.saveTenantGoogleAuthDetials(tenantGoogleAuthDetails);
		return new ApiResponse(201, "SuccessfullyRegistered", tenantGoogleAuthResponse);
	}

}
