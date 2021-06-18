<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<%@ include file="../myinclude/myheader.jsp" %>

 <div id="page-wrapper">
	<div class="row">
		<h3>회원가입이 완료되었습니다.</h3>
	</div>

    <div class="row">
		<button type="button" data-oper="list" class="btn btn-warning"
		                        			onClick="location.href='${contextPath}/login'">로그인</button>
		<span>&nbsp;</span>
		<button type="button" data-oper="list" class="btn btn-warning" onClick="location.href='${contextPath}/'">처음으로</button>
	</div>
</div>

<%@ include file="../myinclude/myfooter.jsp" %>