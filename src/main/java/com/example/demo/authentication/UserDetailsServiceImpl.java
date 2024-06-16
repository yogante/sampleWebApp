package com.example.demo.authentication;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
/**
 * 
 * @author yogamazuka
 *
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	//ユーザー情報テーブル Repository
	private final UserInfoRepository repository;
	
	/** アカウントロックを行うログイン失敗回数境界値 */
	@Value("${security.locking-border-count}")
	private int lockingBorderCount;

	/** アカウントロックの継続時間 */
	@Value("${security.locking-time}")
	private int lockingTime;

	/**
	 * 引き数のログインIDを使ってDBユーザー検索を行い、該当データから後の認証処理で使用するユーザー情報を生成します。
	 * <p>なお、後の認証処理で使用するユーザー情報は以下のように設定します。
	 * <table border=1>
	 * <tr><td>ログインID</td></tr><tr><td>DBに登録されているユーザー情報のログインID</td></tr>
	 * <tr><td>パスワード</td></tr><tr><td>DBに登録されているユーザー情報のパスワード</br>＊後の認証処理専用で利用後はクリアされセッションに保管されません</td></tr>
	 * <tr><td>権限</td></tr><tr><td>DBに登録されているユーザー情報の権限</td></tr>
	 * <tr><td>利用可否</td></tr><tr><td>DBに登録されているユーザー情報の利用可否</td></tr>
	 * <tr><td>アカウントロック</td></tr><tr><td>DBに登録されているユーザー情報のアカウントロック日時と</br>規定のアカウントロック時間（プロパティファイルに設定）からロック解除</td></tr>
	 * </table>
	 * @param username ログインID
	 * @param UsernameNotFoundException DB検索でユーザーが見つからなかった場合
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var userInfo = repository.findById(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));

		var accountLockedTime = userInfo.getAccountLockedTime();
		var isAccountLocked = accountLockedTime !=null
				&&accountLockedTime.plusHours(lockingTime).isAfter(LocalDateTime.now());
		
		return User.withUsername(userInfo.getLoginId())
				.password(userInfo.getPassword())
				// 			.roles("USER")
				.authorities(userInfo.getAuthorityKind().getCode())
				.disabled(userInfo.getUserStatusKind().isDisabled())
				.accountLocked(isAccountLocked)
				// 		.accountExpired(true)  アカウント有効期限切れか
				//		.credentialsExpired(true)  パスワード有効期限切れか
				.build();
	}
	/**
	 * 認証失敗時にログイン失敗回数を加算、ロック日時を更新する
	 * 
	 * @param event イベント情報
	 */
	@EventListener
	public void handle(AuthenticationFailureBadCredentialsEvent event) {
		var loginId = event.getAuthentication().getName();
		repository.findById(loginId).ifPresent(userInfo ->{
			repository.save(userInfo.incrementLoginfailureCount());
			
			var isReachFailureCount = userInfo.getLoginFailureCount()== lockingBorderCount;
			if(isReachFailureCount) {
				repository.save(userInfo.updateAccountLocked());
			}
		});
	}
	
	/**
	 * 認証成功時にログイン失敗回数をリセットする
	 * 
	 * @param event イベント情報
	 */
	@EventListener
	public void handle(AuthenticationSuccessEvent event) {
		var loginId = event.getAuthentication().getName();
		repository.findById(loginId).ifPresent(userInfo->{
			repository.save(userInfo.resetLoginFailureInfo());
		});
	}
}
