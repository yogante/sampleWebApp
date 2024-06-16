package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserDeleteResult {
	
	ERROR(MessageConst.USERLIST_NON_EXISTED_LOGIN_ID),
	
	SUCCEED(MessageConst.USERLIST_DELETE_SUCCEED);

	private String messageId;
	
}
