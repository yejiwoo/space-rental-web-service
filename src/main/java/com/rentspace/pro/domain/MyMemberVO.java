package com.rentspace.pro.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class MyMemberVO {

	//space.member 테이블의 컬럼명과 동일하게 필드이름 설정
	private String member_id;
	private String member_pw;
	private String member_nick;
	private String member_email;
	private String member_phone;
	private int sms_agree;
	private int email_agree;
	private String member_profile_image;
	private int member_level;
	private Timestamp member_join_date;
	
	//아래 2개 칼럼 추가 됨
	private String member_drop_flag; //'0'유지 '1'탈퇴요청
	private boolean member_enabled; // fasle: 비활성화상태, true: 활성화상태 => 휴면계정, 정지상태로 활용
	
	private List<MyAuthorityVO> authorityList;
}
