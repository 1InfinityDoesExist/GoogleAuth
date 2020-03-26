package com.example.googleAuth.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
	static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
