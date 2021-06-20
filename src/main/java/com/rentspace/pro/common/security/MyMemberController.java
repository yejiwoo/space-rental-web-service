package com.rentspace.pro.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rentspace.pro.domain.MyAuthorityVO;
import com.rentspace.pro.domain.MyMemberVO;
import com.rentspace.pro.mapper.MyMemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MyMemberController {

	//사용자 패스워드 암호화 
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;

    
    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;
    
	//로그인 페이지 호출: 로그인 JSP 페이지 이름 문자열 반환
	//로그인 페이지 호출은 GET방식으로만 제한 됨
	//스프링 시큐리티가 반환하는 로그인 처리 결과에 대하여 메시지 처리를 하려면
	//String유형의 error, logout 매개변수가 선언되어야 함
	@GetMapping("/login")
	public String loginPageGET(String error, String logout, Model model) {
		
		if(error!=null) {
			log.info("로그인 오류 시 error.hashCode(): "+error.hashCode());
			model.addAttribute("error","로그인 오류. 계정이나 암호를 확인하세요");
		}else if (logout!=null){ //
			log.info("로그인 오류 시 error.hashCode(): "+logout.hashCode());
			model.addAttribute("logout","정상적으로 로그아웃 됨");
		}else {
			//정상적인 로그인 페이지 호출
			model.addAttribute("normal","정상적인 로그인 페이지 호출 처리..");
			log.info("정상적인 로그인 페이지 호출");
		}
		return "common/myLogin";
	}
	
	//로그아웃 페이지 호출
	//로그아웃 페이지 호출은 GET방식으로만 제한 됨
	@GetMapping("/logout")
	public String logoutPageGET() {
		log.info("로그아웃 페이지만 호출: 로그아웃은 처리 안 됨...");
		return "common/myLogout";
	}
	
	//회원가입 페이지 호출
	@GetMapping("/signup")
	public String signupPageGET() {
		log.info("회원가입 페이지만 호출: 회원가입은 처리 안 됨...");
		return "common/mySignup";
	}
	
	//회원가입 처리
	@PostMapping("/signup")
	public String signup(MyMemberVO myMember, RedirectAttributes redirectAttr) {
		
		myMember.setMember_pw(pwencoder.encode(myMember.getMember_pw()));
		myMemberMapper.insertMyMember(myMember);
		log.info("myMember: "+myMember);

		MyAuthorityVO myAuthority = new MyAuthorityVO();
		
		myAuthority.setMember_id(myMember.getMember_id());
		myAuthority.setAuthority("ROLE_USER");
		
		myMemberMapper.insertMyMemAuthority(myAuthority);
		log.info("myAuthority: "+myAuthority);
		
		return "redirect:/completedSignup";
	}
	
	@GetMapping("/completedSignup")
	public String completedSignup() {
		
		return "common/completedSignup";
	}
	
//	@PostMapping("/idCheck")
	@GetMapping("/idCheck")
	@ResponseBody //return의 String이 .jsp를 반환하지 않고 문자열 그 자체를 반환
	public String idCheck(@RequestParam("member_id") String member_id) {
		log.info("idChck들어옴. member_id: "+member_id);
//		String check= Integer.toString(myMemberMapper.idCheck(member_id));
//		return Boolean.parseBoolean(check);
		return Integer.toString(myMemberMapper.idCheck(member_id));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myPage")
	public String myPage() {
		return "common/myPage";
	}
	
	@GetMapping("/checkReservation")
	public String checkResrv() {
		return "common/checkReservation";
	}
	
	@GetMapping("/modifyProfile")
	public String modifyProfile() {
		return "common/modifyProfile";
	}
}
