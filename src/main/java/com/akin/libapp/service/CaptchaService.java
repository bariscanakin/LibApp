package com.akin.libapp.service;

import org.springframework.stereotype.Service;

import nl.captcha.Captcha;

@Service
public interface CaptchaService {

	Captcha getNewCaptcha();
	
	Boolean checkIfCaptchaIsCorrect(Captcha captcha, String input);
	
}
