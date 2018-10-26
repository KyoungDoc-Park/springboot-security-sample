/**
 * AbstractCipherTypeHandler Abstract class
 * mybatis 에서 사용하는 typehandler
 * 해당 컬럼 조회하거나 입력시 암호화 한다
 * 
 * 컬럼마다 상속받아 사용하며 Application클래스의 sessionFactory.setTypeHandlers 에 입력한다
 */
package com.kdpark.security.util.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kdpark.security.util.AES256Util;




public abstract class AbstractCipherTypeHandler implements TypeHandler<String> {
	
	/** logging  object */
	private static Logger logger = LoggerFactory.getLogger(AbstractCipherTypeHandler.class);
	private String cypherKey = "";
		
	public AbstractCipherTypeHandler(String key) {
		cypherKey = key;
	}

	/* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    	String value = "";
        // 암호화 여부 확인
        if(isCipher()){
        	value = encode(parameter);
        }
        ps.setString(i, value);
    }
 
    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }
 
    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, int)
     */
    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }
 
    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)
     */
    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        // 암호화 여부 확인
        if(isCipher()){
            value = decode(value);
        }
        return value;
    }

	/**
     * 암호화 여부
     * 
     * @return 암호화 여부
     */
    protected abstract boolean isCipher();
    
    /**
     * 암호화
     * 
     * @param value 변환 문자
     * @return 암호화된 문자
     */
    protected String encode(String value){
    	String encValue="";
        try{        	        	
    		AES256Util aes = new AES256Util(cypherKey);
            encValue = aes.aesEncode(value);            
        }catch(Exception e){
        	logger.info(e.toString());
        }
        
        return encValue;
    }
    
    /**
     * 복호화
     * 
     * @param value 변환 문자
     * @return 복호화된 문자
     */
    protected String decode(String value){
    	String encValue="";
        try{
        	AES256Util aes = new AES256Util(cypherKey);
            encValue = aes.aesDecode(value);            
        }catch(Exception e){
        	logger.info(e.toString());
        }
        
        return encValue;
    }

	
}
