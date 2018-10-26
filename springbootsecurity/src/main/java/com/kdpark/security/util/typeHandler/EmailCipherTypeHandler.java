package com.kdpark.security.util.typeHandler;



/**
 * 이메일 암호화 Custom Type Handler
 * 
 * @author Pure
 *
 */

public class EmailCipherTypeHandler extends AbstractCipherTypeHandler {
    
	
	
    public EmailCipherTypeHandler(String key) {
		super(key);		
	}

	/* (non-Javadoc)
     * @see com.pure.test.typehandler.AbstractCipherTypeHandler#isCipher()
     */
    @Override
    protected final boolean isCipher() {    	
        return true;
    }
    
    
}
