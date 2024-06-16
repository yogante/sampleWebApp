package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.constant.db.AuthorityKind;
import com.example.demo.constant.db.UserStatusKind;
import com.example.demo.entity.UserInfo;

/**
 * ユーザー情報テーブルDAO
 * */
public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

	List<UserInfo> findByLoginIdLike(String loginId);

	List<UserInfo> findByLoginIdLikeAndUserStatusKind(String loginId,
			UserStatusKind userStatusKind);

	List<UserInfo> findByLoginIdLikeAndAuthorityKind(String loginId,
			AuthorityKind authorityKind);
	/**
	 * ログインID、アカウント状態、権限の項目を使って検索を行います
	 * 
	 * <p>◾️検索条件
	 * <lu>
	 * <li>ログインID：部分一致</li>
	 * <li>アカウント状態：完全一致</li>
	 * <li>権限の項目：完全一致</li>
	 * </lu></p>
	 * @param loginId
	 * @param userStatusKind
	 * @param authorityKind
	 * @return 検索でヒットしたユーザー情報のリスト
	 */
	List<UserInfo> findByLoginIdLikeAndUserStatusKindAndAuthorityKind(String loginId,
			UserStatusKind userStatusKind, AuthorityKind authorityKind);

		
	}
	

