package com.rentspace.pro.domain;

import lombok.Data;

@Data
public class MyAuthorityVO {

	//space.member_authority 테이블의 컬럼명과 동일하게 필드이름을 설정
	private String member_id;
	private String authority;
}
