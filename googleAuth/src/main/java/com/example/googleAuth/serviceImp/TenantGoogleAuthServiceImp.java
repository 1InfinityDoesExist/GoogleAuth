package com.example.googleAuth.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.googleAuth.model.TenantGoogleAuthDetails;
import com.example.googleAuth.repository.TenantGoogleAuthRepository;
import com.example.googleAuth.service.TenantGoogleAuthService;

@Service
public class TenantGoogleAuthServiceImp implements TenantGoogleAuthService {

	@Autowired
	private TenantGoogleAuthRepository tenantGoogleAuthRepository;

	@Override
	public TenantGoogleAuthDetails saveTenantGoogleAuthDetials(TenantGoogleAuthDetails tenantGoogleAuthDetails) {
		// TODO Auto-generated method stub
		return tenantGoogleAuthRepository.save(tenantGoogleAuthDetails);
	}

	@Override
	public TenantGoogleAuthDetails getTenantGoogleAuthDetails(String tenantId) {
		// TODO Auto-generated method stub
		return tenantGoogleAuthRepository.getTenantGoogleAuthDetailsByTenantId(tenantId);
	}

}
