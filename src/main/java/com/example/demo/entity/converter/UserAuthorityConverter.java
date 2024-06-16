package com.example.demo.entity.converter;

import com.example.demo.constant.db.AuthorityKind;

import jakarta.persistence.AttributeConverter;

public class UserAuthorityConverter implements AttributeConverter<AuthorityKind, String>{
	
	/**
	 * 引数で受け取った権限識別Enumを、権限種別のコード値に変換します
	 * 
	 * @param  権限種別Enum
	 * @return 引数で受け取った権限種別にEnumに保管されているコード値
	 */
	@Override
	public String convertToDatabaseColumn(AuthorityKind authorityKind) {
		return authorityKind.getCode();
	}
	/**
	 * 引数で受け取った権限種別のコード値を、権限種別Enumに変換します。
	 * 
	 * @param 権限種別のコード値
	 * @return 引数で受け取った権限種別のコード値から逆引きした権限種別Enum
	 */
	@Override
	public AuthorityKind convertToEntityAttribute(String value) {
		return AuthorityKind.from(value);
	}
}
