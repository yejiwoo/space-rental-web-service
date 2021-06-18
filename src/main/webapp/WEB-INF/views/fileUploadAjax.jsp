<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}">

	<title>File Upload Ajax Page</title>
</head>
<body>
<h1>Upload With Ajax</h1>
<div class="uploadDiv"><%-- form이 없습니다 --%>
	<input id="inputFile" type="file" name="uploadFiles" multiple><br>
</div>
<button id="btnFileUpload">File Upload With Ajax</button>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> <%-- JQuery 라이브러리 설정 --%>
<%-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>JQuery 라이브러리 설정 --%>

<script>

var csrfToken = $("meta[name='_csrf']").attr("content");
$.ajaxPrefilter(function(options, originalOptions, jqXHR){
    if (options['type'].toLowerCase() === "post") {
        jqXHR.setRequestHeader('X-CSRF-TOKEN', csrfToken);
    }
 });
 
//업로드 파일의 확장자 및 최대 파일 크기 검사 함수
function checkUploadfile(fileName, fileSize){
	//확장자에 대한 정규식 및 최대 허용크기(1MB) 저장변수
	var maxSizePermittedForUploadFile = 1048576;
	var regExpForFileExtention = /(.*)\.(exe|sh|zip|alz)$/i;
	
	//최대 허용 크기 제한 검사
	if(fileSize >= maxSizePermittedForUploadFile){
		alert("업로드 파일의 제한된 크기(1MB)를 초과했습니다.");
		return false;
	}
	
	//업로드파일의 확장자 검사
	if(regExpForFileExtention.test(fileName)){
		alertt("해당 종류(exe/sh/zip/alz)의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
}

//파일업로드 처리
$("#btnFileUpload").on("click", function(e) {
	
	//Ajax 파일 전송 시에 사용되는 클래스
	var formData = new FormData();
	//uploadFiles 이름의 file 유형 input 요소를 변수에 저장
	var inputFiles = $("input[name='uploadFiles']");
	console.log(inputFiles);
	
	//inputFiles에 저장된 파일들을 files 변수에 저장.
	//inputFiles[0]은 첫번째 input 요소를 의미. 단, input 요소가 하나만 있더라도 [0]을 명시해야 함.
	var files = inputFiles[0].files;
	console.log(files);
	
	//formdata 객체에 파일추가
	for(var i = 0; i < files.length ; i++) {
		//파일 확장자 및 최대크기검사 함수 실행 if문
		if(!checkUploadfile(files[i].name, files[i].size)){
			return false;
		}
		
		//uploadFiles 파라미터로 file 정보 추가
		formData.append("uploadFiles", files[i]);
	}
	
	//url 키에 명시된 주소의 컨트롤러에게 formData 객체를 POST 방식으로 전송.
	$.ajax({
		url: '${contextPath}/fileUploadAjaxAction',
		processData: false, //contentType에 설정된 형식으로 data를 처리하지 않음.
		contentType: false, //contentType에 MIME 타입을 지정하지 않음.
		data: formData,
		type: 'post',
		success: function(uploadResult){
			alert("첨부파일의 업로드가 정상적으로 완료되었습니다...");
		}
	})
});

</script>
</body>
</html>