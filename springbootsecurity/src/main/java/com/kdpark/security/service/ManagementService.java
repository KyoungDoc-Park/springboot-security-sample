/**
 * 
 * Management Service
 * Management 메뉴에서 쓰는 API 의 상세 로직 
 * 
 * 대부분의 메소드는 UIReturnEntity 객체를 리턴하며  success, fail를 구분하여 리턴한다
 * 필요시 UIReturnEntity에 "msg", "data" 등 화면에서 사용할 데이타를 넣고 리턴한다
 * 
 * @author kyoungduck.park
 * @since 2018. 03. 15
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *  수정일             수정자                 수정내용
 *  -------      --------         ---------------------------
 *  
 * </pre>
 */
package com.kdpark.security.service;




import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.kdpark.security.mapper.ManagementMapper;
import com.kdpark.security.response.UIReturnEntity;
import com.kdpark.security.security.PasswordEncoder;
import com.kdpark.security.vo.UserAuthVO;
import com.kdpark.security.vo.UserDetailsImplVO;
import com.kdpark.security.vo.UsersVO;

@Service
public class ManagementService {
	
	/** logging  object */
	private static Logger logger = LoggerFactory.getLogger(ManagementService.class);
	
	/** Management mapper injection */
	@Autowired private ManagementMapper mapper;
	
	/** passwordencoder injection */
	@Autowired private PasswordEncoder passwordEncoder;
	
	
	/**
	 * 비밀번호 변경
	 * 사용자 암호를 암호화 하여 변경 저장한다.
	 * 변경할 비밀번호가 현재 비밀번호와 같을 경우 fail
	 * 
	 * @param HashMap<String,String>
	 * @return UIReturnEntity
	 * @throws RuntimeException
	 */
	public UIReturnEntity changePassword(HashMap<String,String> passwords) throws RuntimeException{

		UserDetailsImplVO detail = (UserDetailsImplVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String usePassword = passwords.get("usePassword");
		String newPassword = passwordEncoder.encode(passwords.get("newPassword"));
		
		UsersVO userVo = new UsersVO();			
		userVo.setUserSeq(detail.getUserSeq());
		
		List<UsersVO> returnList = mapper.selectUserPassword(userVo);
		
		UIReturnEntity entity = new UIReturnEntity();
		if(returnList.size()>0) {
			//if equal now password and enter password
			if(passwordEncoder.matches(usePassword, returnList.get(0).getPassword())) {
				userVo.setPassword(newPassword);
				mapper.updateUserPassword(userVo);
			}else {
				entity.setFail("Please check your password");
				return entity;
			}
		}else {
			entity.setFail("There's no user id:"+detail.getUserNm());
			return entity;
		}
		entity.setSuccess("Password changed");		
		return entity;
	}

	
	/**
	 * 권한 코드/명 리스트 불러오기
	 * 
	 * @param 
	 * @return List<UserAuthVO>
	 * @throws RuntimeException
	 */
	public List<UserAuthVO> selectAuth() {		
		return mapper.selectAuth();
	}

	/**
	 * 사용자 추가
	 * ID가 이미 등록되어 있으면 fail
	 * 
	 * @param UsersVO
	 * @return UIReturnEntity
	 * @throws RuntimeException
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	public UIReturnEntity addUser(UsersVO userVo) throws RuntimeException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		UIReturnEntity entity = new UIReturnEntity();
		//check same id
		/*if(mapper.selectSameId(userVo) >0) {
			entity.setFail("'"+userVo.getUserId()+"' is used");
		}
		else {*/		
			userVo.setUserSeq(mapper.selectUserKey());
			UserDetailsImplVO detail = (UserDetailsImplVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userVo.setInsertId(detail.getUserSeq());
			userVo.setApiKey(UUID.randomUUID().toString().replace("-", ""));
			userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));			
			mapper.addUser(userVo); 
			mapper.addUserAuth(userVo);
			entity.setSuccess("Added member.");
//		}
		
		return entity;
	}



}
