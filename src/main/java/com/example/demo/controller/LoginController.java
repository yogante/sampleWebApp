package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
/**
 * ログイン画面　Controller
 * @author yogamazuka
 *
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
	
//	private static final String LOGIN_ID = "user";
//	private static final String PASSWORD = "pwd";
	
	/** サービス画面　service*/
	private final LoginService service;
	
	/** パスワードエンコーダー*/
	private final PasswordEncoder passwordEncoder;

	/** メッセージソース */
	private final MessageSource messageSource;
	
	/** セッション情報 */
	private final HttpSession session;
	
	
	/*
	 * 初期表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return　表示画面
	 * */
	@GetMapping(UrlConst.LOGIN)
	public String view(Model model, LoginForm form) {
		return ViewNameConst.LOGIN;
	}
	/* *
	 * ログインエラー画面表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return　表示画面
	 * */
	@GetMapping(value=UrlConst.LOGIN, params="error")
	public String viewWithError(Model model, LoginForm form) {
		var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("errorMsg", errorInfo.getMessage());
		return ViewNameConst.LOGIN;
	}

	@PostMapping(UrlConst.LOGIN)
	public String login(Model model, LoginForm form) {
		
		var userInfo = service.searchUserById(form.getLoginId());
		
		/**
		 * パスワードエンコードと生のパスワードを比較するため別途作成
		 * var encodedPassword = passwordEncoder.encode(form.getPassword()); 
		 */
		var isCorrectUserAuth = userInfo.isPresent()
				&&passwordEncoder.matches(form.getPassword(), userInfo.get().getPassword());
//		var isCorrectUserAuth = form.getLoginId().equals(LOGIN_ID)
//				&&form.getPassword().equals(PASSWORD);
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		} else {
			var errorMsg = AppUtil.getMessage(messageSource, MessageConst.LOGIN_WRONG_INPUT);
			model.addAttribute("errorMsg", errorMsg);
			return ViewNameConst.LOGIN;
		}
	}
}
