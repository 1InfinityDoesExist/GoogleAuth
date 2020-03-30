package com.example.googleAuth.service;

import org.springframework.stereotype.Service;

import com.example.googleAuth.model.TenantGoogleAuthDetails;

@Service
public interface TenantGoogleAuthService {

	public TenantGoogleAuthDetails saveTenantGoogleAuthDetials(TenantGoogleAuthDetails tenantGoogleAuthDetails);

	public TenantGoogleAuthDetails getTenantGoogleAuthDetails(String tenantId);

}
