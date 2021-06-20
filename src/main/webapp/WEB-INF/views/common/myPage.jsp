<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<%@ include file="../myinclude/myheader.jsp" %>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">
				<!-- 로그인 하지 않은 경우 -principle: anonymousUser String 객체-->
				<!-- 로그인 한 경우 -principle: 로그인 사용자의 Authentication 객체 -->
				<sec:authentication property="principal" var="principal"/>

				<span>${principal.username}님, 반갑습니다.</span>

			</h3>
			<div style="margin:0 auto; width:100%; height:600px; ">
				<div class="myPageContent">
					<h3>
						<a href="${contextPath}/checkReservation">
							<strong>예약확인</strong>
						</a>
					</h3>
				</div>
				<div class="myPageContent">
					<h3>
						<a href="${contextPath}/modifyProfile">
							<strong>회원정보 수정</strong>
						</a>
					</h3>
				</div>
			</div>
		</div>
	</div>
	
	
</div><%-- /.page-wrapper --%>

<%@ include file="../myinclude/myfooter.jsp" %>