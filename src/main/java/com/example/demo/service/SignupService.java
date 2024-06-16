package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;

/**
 * ユーザー登録画面Serviceインターフェース
 */
public interface SignupService {

	/**
	 * 画面の入力情報をもとにユーザー情報テーブルの新規登録を行います。 
	 * 
	 * <p>ただし、以下のテーブル項目はこの限りではありません。
	 * <ul>
	 * <li>パスワード：画面で入力したパスワードがハッシュ化され登録さtれます。</li>
	 * <li>権限：「商品情報の確認が可能」のコード値が登録されます。</li>
	 * </ul>
	 * @param form 入力情報
	 * @param ユーザー情報(ユーザー情報Entity)、すでに同じIDで登録ある場合Empty
	 * */
	
	public Optional<UserInfo> resistUserInfo(SignupForm form);
}
