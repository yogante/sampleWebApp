package com.example.demo.form;

import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;

import lombok.Data;

@Data
public class UserEditForm {

	/** ログイン失敗状況をリセットするか（リセットするならTrue）*/
	private boolean resetsLoginFailure;
	
	/** アカウント状態種類別*/
	private UserStatusKind userStatusKind;
	
	/** ユーザー情報種別*/
	private AuthorityKind authorityKind;
	
	
}
