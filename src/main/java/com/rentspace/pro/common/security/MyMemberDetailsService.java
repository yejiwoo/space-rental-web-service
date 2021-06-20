package com.rentspace.pro.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.rentspace.pro.domain.MyMemberVO;
import  com.rentspace.pro.mapper.MyMemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
//데이터베이스로부터 데이터 가져와서 MyMemberUser객체에 저장하여 Provider에게 전달
//Provider는 MyMemberUser객체를 스프링 시큐리티에게 전달
//스프링 시큐리티는 전달된 데이터를 이용해서 로그인 인증, 페이지 액세스 여부 및 기능 사용에 대한 승인 과정을 처리
public class MyMemberDetailsService implements UserDetailsService {

	//setter방식 주입이 구성되어야 security-context.xml에 정상적인 설정이 가능
	//생성자 주입 방식은 security-context.xml에 설정시에 오류 발생
	@Setter(onMethod_ = {@Autowired})
	private MyMemberMapper myMemberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String member_nick) throws UsernameNotFoundException {
		
		log.info("Load Member By member_nick: "+member_nick);
		
		//스프링시큐리티가 사용하는 username을 매개변수로 사용합니다.
		MyMemberVO myMember = myMemberMapper.selectMyMember(member_nick);
		log.warn("MyMemberMapper에 의해서 반환된 MyMemberVO: "+myMember);
		
		//MyMemberUser 객체 생성 -> UserDetails 유형의 User객체로 변환되어 반환 됨
		return myMember == null? null : new MyMemberUser(myMember);
		
	}

}
