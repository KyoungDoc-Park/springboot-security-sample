/**
 * 
 * 화면 이동 controller
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

package com.kdpark.security.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class URLController {
	
	/** logging  object */
	private static Logger logger = LoggerFactory.getLogger(URLController.class);


	/**
	 * main 화면으로 이동
	 * 로그인한 유저는 InfectedArea, 비로그인 유저는 Login 화면으로 이동
	 * @param
	 * @return 이동할 주소
	 */
	@RequestMapping("/")
	public String indexPage() {
		logger.info("/ ");
		
		//  login이 되어 있을 경우 다른 곳으로 넘긴다		
		SecurityContext context = SecurityContextHolder.getContext();
		if(context.getAuthentication().getAuthorities().size()>0) {
			return "main";
		}else {
			return "login";
		}
		
	}
	
	/**
	 * Login 불가시 처리 로직
	 * - 미승인자
	 * - ID / PASSWORD 오류
	 * 로 구분하여 메시지를 리턴한다
	 * @param
	 * @return 이동할 주소
	 */
	@RequestMapping("/loginError")
	public String loginError(Model model, HttpSession session) {
		logger.info("/loginError ");
		
		if( session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION").getClass() == DisabledException.class) {
			model.addAttribute("message", "You are not allowed\n please contact Aministrator");
		}else if(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION").getClass() == BadCredentialsException.class) {
			model.addAttribute("message", "Please check your ID or Password.");
		}else {
			model.addAttribute("message", "Disable login process");
		}
					
		return "login";
	}
	/**
	 * Log out process
	 * Login 화면으로 이동한다
	 * @param
	 * @return 이동할 주소
	 */
	@RequestMapping("/logoutSuccess")
	public String logout(Model model) {
		logger.info("/logoutSuccess");
		//model.addAttribute("message", "You have been Logged out.");
		return "login";
	}
	
	/**
	 * Login 화면 이동
	 * @param
	 * @return 이동할 주소
	 */
	@RequestMapping("/login")
	public String login() {
		logger.info("/login");
		return "login";
	}
	
	/**
	 * 403 page
	 * @param
	 * @return 이동할 주소
	 */
	@RequestMapping("/403")
	public String go403page() {
		logger.info("/403");
		return "403";
	}
	
	
}
