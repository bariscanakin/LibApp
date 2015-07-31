package com.akin.libapp.model;

import org.springframework.data.annotation.Transient;

public class BookWithCaptcha extends Book {

	@Transient
	private String captchaValue;

	public String getCaptchaValue() {
		return captchaValue;
	}

	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}

}
