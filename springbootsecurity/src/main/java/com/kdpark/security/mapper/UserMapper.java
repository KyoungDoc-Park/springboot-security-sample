package com.kdpark.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kdpark.security.vo.UserAuthVO;
import com.kdpark.security.vo.UsersVO;

@Mapper
public interface UserMapper {
	
	public UsersVO selectUser(String userId);
	public List<UserAuthVO> selectUserAuth(String userId);

}
