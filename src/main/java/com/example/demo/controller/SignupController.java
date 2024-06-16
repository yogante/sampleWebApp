package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
import com.example.demo.constant.signupMessage;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;
/**
 * ユーザー登録画面　Controller
 * @author yogamazuka
 *
 */
@Controller
@RequiredArgsConstructor
public class SignupController {
		
	/** ユーザー登録画面　service*/
	private final SignupService service;


	/** メッセージソース */
	private final MessageSource messageSource ;
	


	/*
	 * 初期表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return　表示画面
	 * */
	@GetMapping(UrlConst.SIGNUP)
	public String view(Model model, SignupForm form) {
		return ViewNameConst.SIGNUP;
	}
	/*
	 * ユーザー登録
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @param bdresult 入力チェック結果
	 * @return　表示画面
	 * */
	@PostMapping(UrlConst.SIGNUP)
	public void signup(Model model, @Validated SignupForm form, BindingResult bdResult) {
		if(bdResult.hasErrors()) {
			
//			var message = AppUtil.getMessage(messageSource, MessageConst.FORM_ERROR);
//			model.addAttribute("message", message);
//			model.addAttribute("isError", true);
//			上記内容をeditGuideMessageというメソッドにまとめた
			editGuideMessage(model, MessageConst.FORM_ERROR, true);			
			return;
		}
		var userInfoOpt = service.resistUserInfo(form);
		
		//		if( userInfoOpt.isEmpty()) {
		//			var errorMsg = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_EXISTED_LOGIN_ID);
		//			model.addAttribute("message", errorMsg);
		//		} else {
		//			var message = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_RESIST_SUCCEED);
		//			model.addAttribute("message", message);
		//		}
		// 　	errorMsg = AppUtil.getMessage(messageSource, MessageConst.とか
		// 		model.addAttribute("message", が繰り返されているから、
		// 		下記のようにjudgeMessageKeyというメソッドを生成して、きれいに修正をかけている
		var signupMessage= judgeMessageKey(userInfoOpt);
//		var messageId = AppUtil.getMessage(messageSource, signupMessage.getMessageId());
//		model.addAttribute("message", messageId);
//		model.addAttribute("isError", signupMessage.isError());
		editGuideMessage(model, signupMessage.getMessageId(), signupMessage.isError());
	}
	/**
	 * 画面に表示するガイドメッセージを設定する
	 * 
	 * @param model　モデル
	 * @param massageId　メッセージID
	 * @param isError　エラー有無
	 */
	private void editGuideMessage(Model model, String messageId, boolean isError) {
		var message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
	}

	
	
	/**
	 * ユーザー情報登録の結果メッセージキーを判断する
	 * @param userInfoOpt　ユーザ登録結果（要録済みだった場合はEmptyに）
	 * @return　メッセージキー　
	 */
	private signupMessage judgeMessageKey(Optional<UserInfo> userInfoOpt) {
		if(userInfoOpt.isEmpty()){
			return signupMessage.EXISTED_LOGIN_ID;
		} else {
			return signupMessage.SUCCEEDED;
		}
	}
}
