package com.example.googleAuth.service;

import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

@Service
public interface GoogleService {

	public String googleLogin();

	public String getGoogleAccessToken(String code);

	public Person getGoogleUserProfile(String accessToken);

}
