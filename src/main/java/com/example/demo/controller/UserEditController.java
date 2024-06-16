package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.SessionKeyConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.UserEditMessage;
import com.example.demo.constant.ViewNameConst;
import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;
import com.example.demo.dto.UserEditInfo;
import com.example.demo.dto.UserUpdateInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.service.UserEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserEditController {

	/** ユーザー一覧画面serviceクラス  */
	private final UserEditService service;
	
	/**　メッセージソース*/
	private final HttpSession session;
	/** dozer mapper*/
	private final Mapper mapper;
	
	/**　メッセージソース*/
	private final MessageSource messageSource;

	@GetMapping(UrlConst.USER_EDIT)
	public String view(Model model, UserEditForm form) throws Exception{
		var loginId = (String) session.getAttribute(SessionKeyConst.SELECTED_LOGIN_ID);
		var userInfoOpt = service.searchUserInfo(loginId);
		
		if(userInfoOpt.isEmpty()) {
			return ViewNameConst.USER_EDIT_ERROR;
}
		setupCommonInfo(model, userInfoOpt.get());
		
		return ViewNameConst.USER_EDIT;
	}


	@PostMapping(value=UrlConst.USER_EDIT, params="update")
	public String updateUser(Model model, UserEditForm form, @AuthenticationPrincipal User user) {
		var updateDto = mapper.map(form,UserUpdateInfo.class);
		updateDto.setLoginId((String) session.getAttribute(SessionKeyConst.SELECTED_LOGIN_ID));
		updateDto.setUpdateUserId(user.getUsername());
		
		var updateResult = service.updateUserInfo(updateDto);
		var updateMessage = updateResult.getUpdateMessage();
		if(updateMessage == UserEditMessage.FAILED) {
			model.addAttribute("message", AppUtil.getMessage(messageSource, updateMessage.getMessageId()));
			return ViewNameConst.USER_EDIT_ERROR;
		}
		setupCommonInfo(model, updateResult.getUpdateUserInfo());
		
		model.addAttribute("isError", false);
		model.addAttribute("message", AppUtil.getMessage(messageSource, updateMessage.getMessageId()));
	
		return ViewNameConst.USER_EDIT;
		
	}
	 
	private void setupCommonInfo(Model model, UserInfo userInfo) {
		model.addAttribute("userEditForm", mapper.map(userInfo, UserEditForm.class));
		model.addAttribute("userEditInfo", mapper.map(userInfo, UserEditInfo.class));
		
		model.addAttribute("userStatusKindOptions", UserStatusKind.values());
		model.addAttribute("authorityKindOptions", AuthorityKind.values());
	}
	
	
	
}
