package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum signupMessage {
	
	SUCCEEDED(MessageConst.SIGNUP_RESIST_SUCCEED, true),
	EXISTED_LOGIN_ID(MessageConst.SIGNUP_EXISTED_LOGIN_ID, true);
	
	private String messageId;
	private boolean isError; 
}
