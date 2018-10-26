package com.kdpark.security.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImplVO extends User{

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	public String userSeq;
	public String userId;
	public String userNm;
	public String countryCd;
	public String telcoCd;
	public String apiKey;
	

	public UserDetailsImplVO(UsersVO vo, List<GrantedAuthority> authorities ) {
		super(vo.getUserId(), vo.getPassword(),vo.getUseYn().equals("Y")?true:false,true,true,true,authorities);
		this.userSeq = vo.getUserSeq();
		this.userId = vo.getUserId();
		this.userNm = vo.getUserNm();	
		this.apiKey = vo.getApiKey();	
	}


	public String getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserNm() {
		return userNm;
	}


	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}


	public String getCountryCd() {
		return countryCd;
	}


	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}


	public String getTelcoCd() {
		return telcoCd;
	}


	public void setTelcoCd(String telcoCd) {
		this.telcoCd = telcoCd;
	}


	public String getApiKey() {
		return apiKey;
	}


	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	
	

}
