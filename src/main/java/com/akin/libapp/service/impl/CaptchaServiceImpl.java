package com.akin.libapp.service.impl;

import org.springframework.stereotype.Component;

import com.akin.libapp.service.CaptchaService;

import nl.captcha.Captcha;

@Component
public class CaptchaServiceImpl implements CaptchaService {

	@Override
	public Captcha getNewCaptcha() {
		Captcha captcha = new Captcha.Builder(200, 50).addText().addBackground().addNoise().addBorder().build();
		return captcha;
	}

	@Override
	public Boolean checkIfCaptchaIsCorrect(Captcha captcha, String input) {
		return captcha.isCorrect(input);
	}

}
