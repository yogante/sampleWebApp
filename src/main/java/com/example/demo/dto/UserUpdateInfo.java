package com.example.demo.dto;

import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;

import lombok.Data;

@Data
public class UserUpdateInfo {
	
	/** ログインID*/
	private String loginId;
	
	/** ログイン失敗状況をリセットするかセットする（リセットならtrue)*/
	private boolean resetsLoginFailure;
	
	/** アカウント状態種類別*/
	private UserStatusKind userStatusKind;
	
	/** ユーザー情報種別*/
	private AuthorityKind authorityKind;
	
	/** 更新ユーザーID*/
	private String updateUserId;

}
