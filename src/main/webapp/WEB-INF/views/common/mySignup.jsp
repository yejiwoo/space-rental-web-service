<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>

	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}">
    
	<title>Sign Up</title>
	
	<!-- Bootstrap Core CSS -->
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="${contextPath}/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${contextPath}/resources/dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!-- jQUery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${contextPath}/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${contextPath}/resources/dist/js/sb-admin-2.js"></script>
    
</head>
<body>

<div id="container">
	<div class="row">
    	<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default" style="margin-top: 10%">
				<div class="panel-heading">
                  	<h2 class="panel-title">회원 가입</h3>
             	</div>
    
                <div class="panel-body">
                    <!-- role: 페이지 탐색을 목적으로 페이지 내의 특정 지점을 묘사한다. 스크린 리더가 이 영역으로 쉽게 이동하도록 만들 수 있다. -->
                	<form role="form" action="${contextPath }/signup" method="post">
	                	<fieldset>
	                    	<div class="form-group">
	                        		<!-- 프로필사진 등록(선택) -->
	                        </div>
	                        <div class="form-group">
	                        		<label>아이디</label>
	                        		<input class="form-control" name="member_id" type="text" id="member_id" required autofocus>
									<div class="check_font" id="id_check"></div>
	                        </div> 
	                       	<div class="form-group">
	                       		<label>비밀번호</label>
	                       		<input class="form-control" name="member_pw" type="password" id="member_pw" placeholder="비밀번호(대소문자, 숫자 포함 8~20 자리)" required>
	                       		<div class="check_font" id="pw_check"></div> 
	                       		
	                       		<input class="form-control" name="member_pw2" type="password" id="member_pw2" placeholder="비밀번호 재입력" onchange="pw_re_ch()" required>
	                       		<div class="check_font" id="pw_check2"></div>
	                       	</div>
	                       	<div class="form-group">
	                       		<label>닉네임</label>
	                       		<input class="form-control" name="member_nick" type="text" id="member_nick" required>
	                       	</div> 
	                       	<div class="form-group">
	                       		<label>이메일</label>
	                       		<input class="form-control" name="member_email" type="email" id="member_email" required>
	                       	</div> 
	                       	<div class="form-group">
	                       		<label>핸드폰번호</label>
	                       		<input class="form-control" name="member_phone" type="tel" id="member_phone" required>
	                       	</div> 
	                       	<div class="checkbox">
	                       		<!-- sms/email agree -->
	                       		<label>
						 			<input name="email_agree" type="checkbox">이메일 수신 동의
								</label>
	                       	</div>
	                       	<div class="checkbox">
	                       		<label>
									<input name="sms_agree" type="checkbox">문자 수신 동의
								</label>
	                       	</div>
	                       	<sec:csrfInput />
	                       	<div>
	                       		<button type="submit" id="reg_submit" class="btn btn-lg btn-success btn-block">회원가입</button>
	                       	</div>
	                    </fieldset>
	                    <fieldset>
	                    	<hr>
	                    	<h5 class="text-center"> 이미 회원이세요?  <a href="${contextPath }/login">로그인</a></h5>
	                    	
	                    	<h4 class="text-center"> 또는 </h4>
	                    	
	                    	<!-- 카카오 로그인 -->
							<a id="custom-login-btn" href="javascript:loginWithKakao()">
						  		<img
								    src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
								    width="222"
						  		/>
							</a>
							
							<!-- 네이버 로그인 -->
	                    </fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>              
</div>
    

<script type="text/javascript">

//csrf ajax 오류 해결 코드
var csrfToken = $("meta[name='_csrf']").attr("content");
$.ajaxPrefilter(function(options, originalOptions, jqXHR){
   if (options['type'].toLowerCase() === "post") {
       jqXHR.setRequestHeader('X-CSRF-TOKEN', csrfToken);
   }
});

//아이디 정규식
var idJ = /^[a-z0-9]{4,12}$/;
//비밀번호 정규식
var pwJ = /^[A-Za-z0-9]{8,20}$/; 

//아이디 유효성 검사(1 = 중복 / 0 != 중복)
$("#member_id").blur(function() {
	// id = "id_reg" / name = "userId"
	var member_id = $('#member_id').val();
	$.ajax({
		 url: '${contextPath}/idCheck?member_id='+member_id,
		 type: 'get',
 		 dataType: 'text', //서버로부터 내가 받는 데이터의 타입
// 		 contentType : 'application/json; charset=utf-8;',//내가 서버로 보내는 데이터의 타입
// 		 data: JSON.stringify(member_id),
		 
		success : function(data) {
			console.log("1 = 중복o / 0 = 중복x : "+ data);							
			
			if (data==1) {
					// 1 : 아이디가 중복되는 문구
					$("#id_check").text("사용중인 아이디입니다");
					$("#id_check").css("color", "red");
					$("#reg_submit").attr("disabled", true);
			}else {
				if(member_id==""){
					$('#id_check').text('아이디를 입력해주세요');
					$('#id_check').css('color', 'red');
					$("#reg_submit").attr("disabled", true);
				}else if(idJ.test(member_id)){
					$('#id_check').text('');
					$("#reg_submit").attr("disabled", false);
					$("#member_id").css("background-color","powderblue");
				}
				else {
					$("#id_check").text("아이디는 소문자와 숫자 4~12자리만 가능합니다");
					$("#id_check").css("color", "red");
					$("#reg_submit").attr("disabled", true);
				}
			}
		}, error : function(request,status,error) {
						console.log("code:"+request.status+"\n"+
								"message:"+request.responseText+"\n"
								+"error:"+error);
					}
	});
});

//비밀번호 유효성 검사
$("#member_pw").blur(function() {
	if(pwJ.test($(this).val())){
		$("#pw_check").text('');
		$("#member_pw").css("background-color","powderblue");
	}else{
		$("#pw_check").text('영문자, 숫자 조합으로 8~20자리를 사용해야 합니다.');
		$("#pw_check").css("color", "red");
		$("#reg_submit").attr("disabled", true);
	}
});

//비밀번호 확인
function pw_re_ch(){
// 	console.log('pw: '+)
	if($("#member_pw").val() == $("#member_pw2").val()){
		$("#pw_check2").text('');
		$("#member_pw2").css("background-color","powderblue");

	}else{
		$("#pw_check2").text('비밀번호가 일치하지 않습니다');
		$("#pw_check2").css("color", "red");
		$("#reg_submit").attr("disabled", true);
	}
}
/* $("#member_pw2").blur(function() {
	if($(this).val() == $('#member_pw').val()){
		$("#pw_check2").text('');
	}else{
		$("#pw_check2").text('비밀번호가 일치하지 않습니다');
		$("#pw_check2").css("color", "red");
		$("#reg_submit").attr("disabled", true);
	}
}); */

//카카오 로그인
function loginWithKakao() {
   Kakao.Auth.login({
     success: function(authObj) {
       alert(JSON.stringify(authObj))
     },
     fail: function(err) {
       alert(JSON.stringify(err))
     },
   })
 }
</script>



</body>
</html>