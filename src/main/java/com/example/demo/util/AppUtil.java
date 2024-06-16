package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 * static なクラスであるため、DIできない
 */
public class AppUtil {
	/**
	 * メッセージからメッセージを取得する
	 * @param messageSource　メッセージソース
	 * @param key　メッセージキー
	 * @param params　置換文字群
	 * @return　メッセージ
	 */
	public static String getMessage(MessageSource messageSource, String key, Object... params) {
		return messageSource.getMessage(key, params, Locale.JAPAN);
	}

	/**
	 * DBのLIKE検索用にパラメーターにワイルドカードを付与します。
	 * @param param パラメーター
	 * @return 前後にワイルドカードが着いたパラメーター
	 */
	public static String addWildCard(String param) {
		return "%" + param + "%" ;
	}
	
	/**
	 * リダイレクト先のURLを受け取って、リダイレクトURL成します。
	 * @param url リダイレクト先のURL
	 * @return リダイレクトのURL
	 */
	public static String doRedirect(String url) {
		return "redirect:" + url;
	}

}
