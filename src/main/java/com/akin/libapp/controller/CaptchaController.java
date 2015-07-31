package com.akin.libapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akin.libapp.constant.SessionKeys;
import com.akin.libapp.model.RestResponse;
import com.akin.libapp.service.CaptchaService;

import nl.captcha.Captcha;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	
	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RestResponse generateCaptcha(HttpSession session) throws IOException {
		Captcha newCaptcha = captchaService.getNewCaptcha();
		session.setAttribute(SessionKeys.CAPTCHA_KEY, newCaptcha);
		
		BufferedImage image = newCaptcha.getImage();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", out);
		byte[] bytes = out.toByteArray();

		String base64bytes = Base64.encodeBase64String(bytes);
		String src = "data:image/png;base64," + base64bytes;
		
		return RestResponse.ok(src);
	}
	
}
