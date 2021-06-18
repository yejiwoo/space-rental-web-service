package com.rentspace.pro.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

//로그인 성공 후에 처리할 내용이 구현된 클래서
//AuthenticationSuccessHandler 인터페이스의 구현객체
@Log4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
									HttpServletResponse response,
									Authentication authentication) //인증객체
											throws IOException, ServletException {

		log.info("로그인 성공 후: MyLoginSuccessHandler.onAuthenticationSuccess() 시작.....");
		log.info("전달된 Authentication 객체 정보: " +authentication);
		
		List<String> roleNames=new ArrayList<>(); //roll 목록을 저장
		//권한 이름 추출
		authentication.getAuthorities() //인증객체에 저장되는 권한들(authorities)을 가져 옴 (배열 반환. 그 배열을 forEach 돌려서 처리)
						.forEach( //각 권한(authority)의 권한이름(role이름)을 리스트객체(roleNames)에 저장
							authority -> { roleNames.add(authority.getAuthority());}
						); //End of forEach
		log.info("ROLE NAMES: "+roleNames) ;//role이름 리스트를 콘솔 표시
		
		if(roleNames.contains("ROLE_ADMIN")) response.sendRedirect("/rentspace/");
		else response.sendRedirect("/ex00/");
	}

	

}
