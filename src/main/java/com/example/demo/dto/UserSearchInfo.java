package com.example.demo.dto;

import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;

import lombok.Data;

@Data
public class UserSearchInfo {

	/** ログインID*/
	private String loginId;
	
	/** アカウント状態種類別*/
	private UserStatusKind userStatusKind;
	
	/** ユーザー情報種別*/
	private AuthorityKind authorityKind;
	

}
