package com.example.googleAuth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.googleAuth.model.TenantGoogleAuthDetails;

@Repository
@Transactional
public interface TenantGoogleAuthRepository extends JpaRepository<TenantGoogleAuthDetails, Long> {

	@Query("Select TenantGoogleAuthDetails from #{#entityName} TenantGoogleAuthDetails where tenantId=?1")
	public TenantGoogleAuthDetails getTenantGoogleAuthDetailsByTenantId(String tenantId);

}
