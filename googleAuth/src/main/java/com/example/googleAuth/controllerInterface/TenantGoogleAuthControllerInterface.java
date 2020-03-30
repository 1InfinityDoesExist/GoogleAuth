package com.example.googleAuth.controllerInterface;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.googleAuth.model.ApiResponse;
import com.example.googleAuth.model.TenantGoogleAuthDetails;

@Component
public interface TenantGoogleAuthControllerInterface {

	@RequestMapping(path = "/{tenantId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ApiResponse saveTenantGoogleAuthDetails(
			@Valid @RequestBody(required = true) TenantGoogleAuthDetails tenantGoogleAuthDetails,
			@PathVariable(value = "tenantId", required = true) String tenantid);

}
