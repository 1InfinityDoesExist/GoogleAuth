package com.example.googleAuth.serviceImp;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.googleAuth.service.SecurityService;

@Service
@Transactional
public class SecurityServiceImp implements SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	// User is providing password or login via social group
	@Override
	public void autoLoginIn(String email, String password, String role, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (StringUtils.hasText(password)) {
			UserDetails details = userDetailsService.loadUserByUsername(email);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password, details.getAuthorities()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		;
	}

}
