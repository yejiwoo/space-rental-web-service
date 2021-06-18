<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>접근제한</title>
</head>

<body>
	<h2>해당 페이지에 접근이 제한됨</h2>
	<fieldset>
		<h4>회원님은 해당 페이지를 이용하실 수 없습니다.</h4>
	</fieldset>
	<br>
	<fieldset>
		<h4>getMessage(): <c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}"/></h4> <!-- Access is denied 출력 됨 -->
		<hr>
		<h4>msg: <c:out value="${msg}"/></h4>
	</fieldset>
</body>
</html>