package com.example.demo.constant;
/**
 * 
 * @author yogamazuka
 *
 */

public class UrlConst {
	
	/** ログイン画面*/
	public static final String LOGIN= "/login";
	
	/** 登録画面*/
	public static final String SIGNUP= "/signup";
	
	/** メニュー画面*/
	public static final String MENU= "/menu";

	/** ユーザー一覧画面*/
	public static final String USER_LIST= "/userList";

	/** ユーザー編集画面*/
	public static final String USER_EDIT = "/userEdit";

	/** 認証不可画面*/
	public static final String[] NO_AUTHENTICATION = {LOGIN,SIGNUP, "/webjars/**", "/css/**"};

}
