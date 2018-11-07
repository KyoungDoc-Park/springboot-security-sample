/**
 * 
 * Management Mapper interface
 * classpath:/mappers/ManagementMapper.xml 에 해당하는 SQL 수행 역할
 * (sqlSessionTemplate을 직접 사용하지 않고 추상메소드 만 정의하여 사용함)
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
package com.kdpark.security.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kdpark.security.vo.PagingVO;
import com.kdpark.security.vo.UserAuthVO;
import com.kdpark.security.vo.UsersVO;


@Mapper
public interface ManagementMapper {

	/** 비밀번호 조회 */
	List<UsersVO> selectUserPassword(UsersVO userVo);

	/** 비밀번호 변경 */
	void updateUserPassword(UsersVO userVo);

	/** 모든 사용자 불러오기 */
	List<UsersVO> loadUsers(PagingVO pagingVo);
	
	/** 권한 코드/명 리스트 불러오기*/
	List<UserAuthVO> selectAuth();

	/** 사용자 추가*/
	void addUser(UsersVO userVo);

	/** 사용자 권한 추가*/
	void addUserAuth(UsersVO userVo);
	
	/** 사용자 key 값 조회 */
	String selectUserKey();

}
