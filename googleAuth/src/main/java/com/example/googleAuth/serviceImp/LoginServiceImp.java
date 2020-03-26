package com.example.googleAuth.serviceImp;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.googleAuth.exception.GoogleAuthException;
import com.example.googleAuth.model.UserInfo;
import com.example.googleAuth.repository.UserInfoRepository;
import com.example.googleAuth.service.LoginService;

@Service
@Transactional
public class LoginServiceImp implements LoginService {
	@Autowired
	private UserInfoRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		UserInfo user = userRepository.findByEmailAndEnabled(email, true);
		if (user == null) {
			throw new GoogleAuthException("User not Found for email:- " + email);
		}
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
		UserDetails userDetails = (UserDetails) new User(user.getEmail(), user.getPassword(),
				(Collection<? extends GrantedAuthority>) Arrays.asList(authority));
		return userDetails;
	}

}
