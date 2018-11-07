# springboot-security-sample
spring boot + security 
커스터마이징한 User 객체를 이용하여 사용자 인증을 한다.

## important codes
 - security.WebSecurityConfig.java : 웹보안 설정
 - service.WebSecurityConfig.java  : 사용자 인증 로직
 -  vo.UserDetailsImpleVO.java      : 사용자 객체 (extends org.springframework.security.core.userdetails.User class)

## dependencies
 - spring boot 1.5
 - security
 - mybatis
 - thymeleaf
 - postgres

## Data
 1. db_script/create_db.sql 실행
 2. data insert
 
