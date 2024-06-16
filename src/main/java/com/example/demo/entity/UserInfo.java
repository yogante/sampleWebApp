package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;
import com.example.demo.entity.converter.UserAuthorityConverter;
import com.example.demo.entity.converter.UserStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * ユーザー情報テーブル　Entity
 * */
@Entity
@Table(name = "user_info")
@Data
@AllArgsConstructor
public class UserInfo {
	
	/** ログインID */
	@Id
	@Column(name = "login_id")
	private String loginId;

	/** パスワード*/
	private String password;

	/** メールアドレス*/
	@Column(name = "mail_address")
	private String mailAddress;
	
	/** ワンタイムコード*/
	@Column(name = "one_time_code")
	private String oneTimeCode;
	
	/** ワンタイムコード有効期限*/
	@Column(name = "one_time_code_send_time")
	private LocalDateTime oneTimeCodeSendTime;
	
	/** ログイン失敗回数 */
	@Column(name = "login_failure_count")
	private int loginFailureCount = 0;

	/** アカウントロック日時 */
	@Column(name = "account_locked_time")
	private LocalDateTime accountLockedTime;

	/** ユーザー状態識別　利用可能か(true:利用可能) */
	@Column(name = "is_disabled")
	@Convert(converter = UserStatusConverter.class)
	private UserStatusKind userStatusKind;
	
	/** ユーザー権限識別*/
	@Column(name= "authority")
	@Convert(converter = UserAuthorityConverter.class)	
	private AuthorityKind authorityKind;
	
	/** アカウントロック日時 */
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/** アカウントロック日時 */
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**  最終更新ユーザー*/
	@Column(name = "update_user")
	private String updateUser;

	
	/** デフォルトコンストラクタ*/
	public UserInfo() {
	}

	/**
	 * ログイン失敗回数をインクリメントする
	 * 
	 * @return ログイン失敗回数がインクリメントされたUserInfo
	 */
	public UserInfo incrementLoginfailureCount() {
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, ++loginFailureCount, accountLockedTime, userStatusKind, authorityKind, createTime, updateTime, updateUser);
	}
	
	/**
	 * ログイン失敗情報をリセットする
	 * 
	 * @return ログイン失敗情報がリセットされたUserInfo
	 */
	public UserInfo resetLoginFailureInfo() {
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, 0, null,  userStatusKind, authorityKind, createTime, updateTime, updateUser);
	}
	
	/**
	 * アカウントロック状態に更新する
	 * 
	 * @return ログイン失敗階位数、アカウントロック日時が更新されたUserInfo
	 */
	public UserInfo updateAccountLocked() {
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, 0, LocalDateTime.now(),  userStatusKind, authorityKind, createTime, updateTime, updateUser);
	}


}
