package com.example.googleAuth.serviceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;

import com.example.googleAuth.service.GoogleService;

@Component
public class GoogleServiceImp implements GoogleService {
	private static final Logger logger = LoggerFactory.getLogger(GoogleServiceImp.class);

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleId;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleSecret;

	private GoogleConnectionFactory createGoogleConnection() {
		return new GoogleConnectionFactory(googleId, googleSecret);
	}

	@Override
	public String googleLogin() {
		// TODO Auto-generated method stub
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:8080/login/oauth2/code/google");
		parameters.setScope("profile email");
		return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getGoogleAccessToken(String code) {
		return createGoogleConnection().getOAuthOperations()
				.exchangeForAccess(code, "http://localhost:8080/google", null).getAccessToken();

	}

	@Override
	public Person getGoogleUserProfile(String accessToken) {
		// TODO Auto-generated method stub
		Google google = new GoogleTemplate(accessToken);
		Person person = google.plusOperations().getGoogleProfile();
		return person;
	}

}
