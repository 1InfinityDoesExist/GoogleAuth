package com.example.googleAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.googleAuth.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	@Query("Select UserInfo from #{#entityName} UserInfo where email = ?1 and enabled = ?2")
	public UserInfo findByEmailAndEnabled(String email, boolean enabled);

	@Query("Select UserInfo from #{#entityName} UserInfo where email = ?1")
	public UserInfo findByEmail(String accountEmail);

}
