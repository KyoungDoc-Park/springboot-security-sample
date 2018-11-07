/**
 * 
 * UIReturnEntity
 * UI와 서버단 간의 주가 받는 데이터 형식
 * HashMap에 key, value형태로 통신한다
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
package com.kdpark.security.response;

import java.util.HashMap;

public class UIReturnEntity  {
	
	
	HashMap<String,Object> returnMap;
	
	
	public UIReturnEntity() {
		returnMap = new HashMap<String,Object>();
		returnMap.put(Constants.RESULT, Constants.FAIL);
	}
	
	
	public void setSuccess(String msg) {
		returnMap.put(Constants.RESULT, Constants.SUCCESS);		
		returnMap.put(Constants.MESSAGE, msg);
	}
	public void setSuccess() {
		returnMap.put(Constants.RESULT, Constants.SUCCESS);		
	}
	public void setData(String key, Object data) {
		returnMap.put(key, data);
	}
	
	public void setFail(String msg) {
		returnMap.put(Constants.RESULT, Constants.FAIL);		
		returnMap.put(Constants.MESSAGE, msg);
	}

	public HashMap<String, Object> getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(HashMap<String, Object> returnMap) {
		this.returnMap = returnMap;
	}
	
	
	
	

}
