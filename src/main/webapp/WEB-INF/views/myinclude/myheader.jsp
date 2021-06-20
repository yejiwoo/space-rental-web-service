<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>my Admin Board</title>

    <!-- Bootstrap Core CSS -->
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${contextPath}/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <%-- <link href="${contextPath}/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet"> --%>

    <!-- DataTables Responsive CSS -->
    <%-- <link href="${contextPath}/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet"> --%>

    <!-- Custom CSS -->
    <link href="${contextPath}/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery -->
    <%-- <script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script> --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${contextPath}/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
<%--     <script src="${contextPath}/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath}/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="${contextPath}/resources/vendor/datatables-responsive/dataTables.responsive.js"></script> --%>

    <!-- Custom Theme JavaScript -->
    <script src="${contextPath}/resources/dist/js/sb-admin-2.js"></script>

<!-- favicon.ico 404 Error 해결 -->
<!-- <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon"> --><!-- favicon 사용 안함 -->
<%-- <link rel="shortcut icon" href="${contextPath}/resources/img/favicon-penguin.png" type="image/png"> --%>


</head>

<body id="me">

   <div id="wrapper" style="background-color: #ffffff;">

      <!-- Navigation -->
      <nav class="navbar navbar-default navbar-static-top" role="navigation" style="background-color: #ffffff !important;margin-bottom: 0px;">
         <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
               data-target=".navbar-collapse">
               <span class="sr-only">Toggle navigation</span> <span
                  class="icon-bar"></span> <span class="icon-bar"></span> <span
                  class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${contextPath}">RENTSPACE</a>
         </div>
         <!-- /.navbar-header -->

         <!-- search bar -->
<!--          <div class="input-group custom-search-form"
            style="float: left; width: 15%; padding: 8px 0px">
            <input type="text" class="form-control" placeholder="Search">
            <span class="input-group-btn">
               <button class="btn btn-default" type="button">
                  <i class="fa fa-search"></i>
               </button>
            </span>
         </div> -->
         <!-- /input-group -->



         <ul class="nav navbar-top-links navbar-right">
            

            <li class="dropdown" style="cursor: pointer;">
	           <a class="dropdown-toggle" data-toggle="dropdown"> 
	               <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
	           </a>
	           
	           <sec:authentication property="principal" var="principal"/> 
	           <ul class="dropdown-menu">
		           <c:choose>
						<c:when test="${principal eq 'anonymousUser' }">
							<li><a id="freeSignup" href="${contextPath}/signup">
									<i class="fa fa-sign-in fa-fw"></i> 회원가입
                  				</a>
                  			</li>
                  			<li class="divider"></li>
							<li><a id="freeLogin" href="${contextPath}/login">
									<i class="fa fa-sign-in fa-fw"></i> 로그인
                  				</a>
                  			</li>
						</c:when>
						<c:otherwise>
							<span>${principal.username}님</span>
							<li class="divider"></li>
							<li><a href="${contextPath}/myPage"><i class="fa fa-home fa-fw"></i>
                     			마이페이지</a></li>
                     		<li class="divider"></li>
			                <li><a id="freeLogout" href="${contextPath}/logout"> <i
			                        class="fa fa-sign-out fa-fw"></i> 로그아웃
			                  	</a>
                 			</li>
						</c:otherwise>
					</c:choose>
               
                 
                  
               </ul> <!-- /.dropdown-user --></li>
            <!-- /.dropdown -->
         </ul>
         <!-- /.navbar-top-links -->

         <div class="navbar-default sidebar" role="navigation" style="background-color: #ffffff">
            <div class="sidebar-nav navbar-collapse">
               <ul class="nav" id="side-menu">
<%--                   <li><a href="${contextPath}/mypage/mypage"><i class="fa fa-home fa-fw"></i>
                        마이페이지</a></li> --%>
                  <li><a href="${contextPath}/notice/list"><i
                        class="fa fa-table fa-fw"></i> 공지사항</a></li>
                  <li><a href="${contextPath}/freeboard/list"><i
                        class="fa fa-edit fa-fw"></i>자유게시판</a></li>
                  <li><a href="${contextPath}/space/space"><i
                        class="fa fa-sign-in fa-fw"></i>공간등록하기</a></li>
               </ul>
            </div>
            <!-- /.sidebar-collapse -->
         </div>

         <!-- /.navbar-static-side -->
      </nav>