package com.example.demo.constant.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusKind {

	/** 使用可能*/
	ENABLED(false, "使用可能"),
	
	/** 使用不可*/
	DISABLED(true, "使用不可");

	/** DB登録値*/
	private boolean isDisabled;

	/** 画面表示する説明文*/
	private String displayValue;

}
