package com.kdpark.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kdpark.security.mapper.UserMapper;
import com.kdpark.security.vo.UserAuthVO;
import com.kdpark.security.vo.UserDetailsImplVO;
import com.kdpark.security.vo.UsersVO;


@Service
public class UserAuthenticationService implements UserDetailsService{

	private static Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
	
	@Autowired UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		UserDetailsImplVO userDetailVO = null;
						
		UsersVO userVo = userMapper.selectUser(userId);
		 
		
		if(userVo != null) {
			//권한 가져오기
			List<UserAuthVO> auths = userMapper.selectUserAuth(userVo.getAuthCd());
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			if(auths.size()>0) {				
				//ROLE code				
				 authorities.add(new SimpleGrantedAuthority("ROLE_"+userVo.getAuthCd()));
				for(UserAuthVO auth : auths){
					//auth's menu list
					 authorities.add(new SimpleGrantedAuthority(auth.getMenuId()));
				}
			}else {
				logger.info("has no authorization");
				throw new UsernameNotFoundException(userId);
			}
			//기본정보 , 권한 입력
			userDetailVO = new UserDetailsImplVO(userVo,authorities);
			
		}else {
			logger.info("no matching id");
			throw new UsernameNotFoundException(userId);
		}
		return userDetailVO;
	}

}
