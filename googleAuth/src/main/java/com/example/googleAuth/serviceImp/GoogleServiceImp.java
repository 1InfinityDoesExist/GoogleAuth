package com.example.googleAuth.serviceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.example.googleAuth.model.TenantGoogleAuthDetails;
import com.example.googleAuth.service.GoogleService;

@Component
public class GoogleServiceImp implements GoogleService {
	private static final Logger logger = LoggerFactory.getLogger(GoogleServiceImp.class);

	private TenantGoogleAuthServiceImp tenantGoogleAuthDetails;

	public GoogleServiceImp(TenantGoogleAuthServiceImp tenantGoogleAuthDetails) {
		this.tenantGoogleAuthDetails = tenantGoogleAuthDetails;
	}

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleId;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleSecret;

	private GoogleConnectionFactory createGoogleConnection() {
		return new GoogleConnectionFactory(googleId, googleSecret);
	}

	// Google Authentication via TenantGoogleAuthDetails From
	// ApplicationProperties File
	@Override
	public String googleLogin() {
		// TODO Auto-generated method stub

		// To read from env...!!!
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:8080/login/oauth2/code/google");
		parameters.setScope("profile email");
		return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	public GoogleConnectionFactory createGoogleConnetionFactory(String tenantId) {
		TenantGoogleAuthDetails tenantGoogleAuthDetailsFromDB = tenantGoogleAuthDetails
				.getTenantGoogleAuthDetails(tenantId);
		String clientId = tenantGoogleAuthDetailsFromDB.getClientId();
		String clientSecret = tenantGoogleAuthDetailsFromDB.getClientSecret();
		return new GoogleConnectionFactory(clientId, clientSecret);
	}

	// Google Authentication via TenantGoogleAuthDetails From DB Details File
	// Returns Url Of User GoolgeLogin Page
	@Override
	public String googleLoginViaTenantGoolgeAuthDetailsFromDB(String tenantId) {
		// TODO Auto-generated method stub
		TenantGoogleAuthDetails tenantGoogleAuthDetailsFromDB = tenantGoogleAuthDetails
				.getTenantGoogleAuthDetails(tenantId);

		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(tenantGoogleAuthDetailsFromDB.getRedirectUrl());
		parameters.setScope("profile email");
		return createGoogleConnetionFactory(tenantId).getOAuthOperations().buildAuthenticateUrl(parameters);

	}

	@Override
	public String getGoogleAccessToken(String code) {
		logger.info("Inside getGoogleAccessToken method of GoogleServiceImp class");

		System.out.println("Code is ------------> " + code);

		return createGoogleConnection().getOAuthOperations()
				.exchangeForAccess(code, "http://localhost:8080/login/oauth2/code/google", null).getAccessToken();

		// try {
		// AccessGrant accessGrant =
		// createGoogleConnection().getOAuthOperations().exchangeForAccess(code,
		// "http://localhost:8080/login/oauth2/code/google", null);
		// return accessGrant.getAccessToken();
		// } catch (HttpClientErrorException e) {
		// logger.warn("HttpClientErrorException while completing connection: "
		// + e.getMessage());
		// logger.warn(" Response body: " + e.getResponseBodyAsString());
		// throw e;
		// }

	}

	@Override
	public Person getGoogleUserProfile(String accessToken) {
		// TODO Auto-generated method stub
		logger.info("getGoogleUserProfile method in GoogleServiceImp Class");
		Google google = new GoogleTemplate(accessToken);
		logger.info("Google ****************---------------->" + google.plusOperations());
	//	Person person = google.plusOperations().getGoogleProfile();
		Person person = google.plusOperations().getGoogleProfile();
		logger.info("Person ********************------------->" + person);
		return person;
	}

}
