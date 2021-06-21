package com.manageuniversity.controller.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
	private String token;
	private String password;

}
