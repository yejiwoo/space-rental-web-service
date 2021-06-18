package com.rentspace.pro.common.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.rentspace.pro.domain.MyMemberVO;

import lombok.Getter;

@Getter //myMember에 대한 getter
public class MyMemberUser extends User{

	private static final long serialVersionUID= 1L;
	private MyMemberVO myMember;
	
	//생성자를 통해 부모 클래스 객체(User객체)에 username, password, 부여된 authority 목록을 설정
	public MyMemberUser(MyMemberVO myMember) {
		super(myMember.getMember_id(), //스프링 시큐리티의 username으로 사용 됨
				myMember.getMember_pw(), //스프링 시큐리티의 password로 사용 됨
				myMember.getAuthorityList() //스프링 시큐리티의 authorities로 사용 됨
						.stream() //AuthorityVO 리스트를 스트림으로 변환
						.map(auth -> new SimpleGrantedAuthority(auth.getAuthority())) //각 권한을 Grant된 권환으로 변환
						.collect(Collectors.toList()) //스트림을 리스트(부여된 권한이름만 저장)로 변환
			);
		
		System.out.println("MyMemberUser 생성자에 전달된 MyMemberVO 정보: "+myMember.toString());
		System.out.println("MyMemberUser 객체 생성을 통해 MyMemberUser의 부모객체(User객체) 생성 됨");
		System.out.println("=======================");
		
		this.myMember=myMember;
	}
	
	
	
}
