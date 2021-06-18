package com.rentspace.pro.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.rentspace.pro.domain.MyAuthorityVO;
import com.rentspace.pro.domain.MyMemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/security-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

@Log4j
public class MyMemberMapperTests {

    //사용자 패스워드 암호화 
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;

    
    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;


    //회원 등록 테스트 1
//    @Test
//    public void testInsertMyMember() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            myMember.setMember_pw(pwencoder.encode("pw" + i));
//
//            if(i < 80) {
//                myMember.setMember_id("user" + i);
//                myMember.setMember_nick("일반사용자" + i);
//                myMember.setMember_email("user"+i+"@goott.com");
//            
//            } else if (i < 90) {
//                myMember.setMember_id("manager" + i);
//                myMember.setMember_nick("운영자" + i);
//                myMember.setMember_email("manager"+i+"@goott.com");
//                
//            } else {
//                myMember.setMember_id("admin" + i);
//                myMember.setMember_nick("관리자" + i);
//                myMember.setMember_email("admin"+i+"@goott.com");
//            }
//            
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        } //for-End
//    }

//    
//    //회원 권한 등록 테스트
//    @Test
//    public void testInsertMyMemAuthority() {
//		
//        MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            if(i < 80) {
//                myAuthority.setMember_id("user" + i);
//                myAuthority.setAuthority("ROLE_USER");
//              
//            } else if (i < 90) {
//                myAuthority.setMember_id("manager" + i);
//                myAuthority.setAuthority("ROLE_MANAGER");
//              
//            } else {
//                myAuthority.setMember_id("admin" + i);
//                myAuthority.setAuthority("ROLE_ADMIN");
//              
//            }
//            log.info(myAuthority);
//            myMemberMapper.insertMyMemAuthority(myAuthority);
//        } //for-End
//      
//        myAuthority.setMember_id("admin99");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//      
//        myAuthority.setMember_id("admin99");
//        myAuthority.setAuthority("ROLE_USER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//      
//        myAuthority.setMember_id("admin91");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//      
//    }
//  
    
//    //회원 정보 조회 테스트    
//    @Test
//    public void testRead() {
//        MyMemberVO myMember = myMemberMapper.selectMyMember("admin99");
//        
//        log.info(myMember);
//      
//        myMember.getAuthorityList().forEach(authorityVO -> log.info(authorityVO));
//    }

    
//추가 회원 등록
//  
    //회원 등록 테스트2 -  test 사용자 등록(10명) 
//    @Test
//    public void testInsertMyMember2() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i < 10; i++) {
//        	myMember.setMember_pw(pwencoder.encode("pw" + i));
//            myMember.setMember_id("test" + i);
//            myMember.setMember_nick("테스트사용자" + i);
//            myMember.setMember_email("test"+i+"@goott.com");
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        } //for-End
//    }

//  

//    //회원 권한 테스트2 - test 사용자 권한 설정 
//    @Test
//    public void testInsertMyMemAuthority2() {
//    	
//    	MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i < 10; i++) {
//        	myAuthority.setMember_id("test" + i);
//        	myAuthority.setAuthority("USER");
//            log.info(myAuthority);            
//            myMemberMapper.insertMyMemAuthority(myAuthority);
//        } //for-End
//    }
// 
//    //회원 등록 테스트3 - test99 사용자 등록 
//    @Test
//    public void testInsertMyMember3() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//       
//        	myMember.setMember_pw(pwencoder.encode("pw99"));
//            myMember.setMember_id("test99");
//            myMember.setMember_nick("테스트사용자99");
//            myMember.setMember_email("test99@goott.com");
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        
//    }

    

//    //회원 권한 테스트2 - test99 사용자: ROLE_USER 권한 부여 
    @Test
    public void testInsertMyMemAuthority2() {
  	
        MyAuthorityVO myAuthority = new MyAuthorityVO();
       
        myAuthority.setMember_id("test99");
        myAuthority.setAuthority("MYUSER");
        log.info(myAuthority);
        myMemberMapper.insertMyMemAuthority(myAuthority);
        
        myAuthority.setMember_id("test99");
        myAuthority.setAuthority("MYMANAGER");
        log.info(myAuthority);
        myMemberMapper.insertMyMemAuthority(myAuthority);
        
        myAuthority.setMember_id("test99");
        myAuthority.setAuthority("MYADMIN");
        log.info(myAuthority);
        myMemberMapper.insertMyMemAuthority(myAuthority);

    }

 

    

 

    

 

}

 

 

/*

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;

 

import com.spring5212.mypro00.common.security.MyMemberVO;

 

import lombok.Setter;
import lombok.extern.log4j.Log4j;

 

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
@Log4j
public class MyMemberMapperTests {

 

    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;

	  

	  
    @Test
    public void testRead() {
        MyMemberVO myMember = myMemberMapper.selectUser("admin99");
        log.info(myMember);
   

        myMember.getAuthorityList().forEach(authVO -> log.info(authVO));
    }


}

*/