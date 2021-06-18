package com.rentspace.pro.common.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class FileUploadController {

	private static final Logger logger=LoggerFactory.getLogger(FileUploadController.class);
	
	//저장경로 (Windows환경이므로 경로구분자를 \\로 지정)
	private String strUploadFileRepoDir ="C:\\upload";
	
	//다중파일 업로드 방법1: form 방식의 파일업로드
	//파일 업로드 요청 JSP 페이지 호출
//	@GetMapping("/fileUploadForm")
//	public void callFileUploadForm() {
//		logger.info("upload Form");
//	}
//	
//	//다중파일 업로드 방법1: form 방식의 파일업로드
//	//Model이용, 업로드 파일 저장
//	@PostMapping("/fileUploadFormAction")
//	public void fileUploadActionPost(MultipartFile[] uploadFiles, Model model) {
//		logger.info("==FileUpload With Form ===");
//		
//		//저장경로 (Windows 환경이므로 경로구분자를 \\로 지정)
//		String strUploadFileRepoDir="C:\\upload";
//		
//		for(MultipartFile multipartUploadFile : uploadFiles) {
//			logger.info("=================");
//			logger.info("Upload File Name: "+multipartUploadFile.getOriginalFilename());
//			logger.info("Upload File Size: "+multipartUploadFile.getSize());
//			
////			//업로드 파일의 리소스(저장폴더와 파일이름)가 설정된 File객체 생성
////			File saveUploadFile = new File(strUploadFileRepoDir, multipartUploadFile.getOriginalFilename());
//			
//			//internet explorer에서도 정상작동되도록, 위의 코드를 아래의 코드로 대체
//			String strUploadFileName = multipartUploadFile.getOriginalFilename();
//			//IE10 파일이름 추출: multipartUploadFile.getOriginalFilename()에서 업로드 파일이름만 추출
//			//파일 이름만 있는 경우, 파일이름만 추출 됨
//			strUploadFileName=strUploadFileName.substring(strUploadFileName.lastIndexOf("\\")+1);
//			//업로드정보(저장 폴더와 파일이름 문자열)의 파일객체 생성
//			File saveUploadFile=new File(strUploadFileRepoDir, strUploadFileName);
//			
//			try {
//				//파일객체(saveUploadFile)를 이용하여 서버에 업로드파일 저장
//				multipartUploadFile.transferTo(saveUploadFile);
//				
//			}catch(Exception e) {
//				logger.error(e.getMessage());
//			}
//		}//End-for
//	}
	
	//다중파일 업로드 방법2: Ajax 방식의 파일 업로드
	//사용자의 업로드 요청 페이지 호출
	@GetMapping("/fileUploadAjax")
	public void callFileUploadAjax() {
		logger.info("upload Ajax Form");
	}
	
	//Ajax 썸네일 이미지 생성 Step1: 업로드 파일이 이미지 파일인지 검사
	private boolean checkIsImageForUploadFile(File uploadFile) {
		try {
			String strContentType = Files.probeContentType(uploadFile.toPath());
			System.out.println("업로드파일의 ContentType: "+strContentType);
			
			return strContentType.startsWith("image");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//업로드 요청 파일 저장 및 결과 메시지 전송
	//Ajax 썸네일 이미지 생성 Step2: 이미지파일 여부 확인 후, 이미지파일의 썸네일 생성 저장
	@PostMapping("/fileUploadAjaxAction")
	public void fileUploadActionAjaxPost(MultipartFile[] uploadFiles) { //Ajax 사용시 Model 필요 없음
		logger.info("=======Start FileUpload With Ajax=======");
		
		//각각의 업로드파일 이름을 구성한 후, 저장경로와 파일이름으로 생성된 파일 객체를 서버에 저장
		for(MultipartFile multipartUploadFile: uploadFiles) {
			logger.info("=====================================");
			logger.info("multipartUploadFile: " + multipartUploadFile);
			logger.info("Upload File Name: "+multipartUploadFile.getOriginalFilename());
			logger.info("Upload File size: "+multipartUploadFile.getSize());
			
			//업로드 파일 이름 원본 문자열
			String strUploadFileName =multipartUploadFile.getOriginalFilename();
			
			//IE10 파일이름추출: multipartUploadFile.getOriginalFilename()에서 업로드 파일이름만 추출
			//파일이름만 있는 경우, 파일 이름만 추출됨
			strUploadFileName = strUploadFileName.substring(strUploadFileName.lastIndexOf("\\")+1);
			logger.info("수정된 파일 이름(strUploadFileName): "+strUploadFileName);
			
			//UUID를 이용한 고유한 파일이름 적용
			//랜덤한 UUID값을 가진 UUID 객체 생성
			UUID uuid = UUID.randomUUID();
			//파일 이름에 UUID 문자열 추가(파일 확장자 때문에 UUID를 앞에다 추가해야 함)
			strUploadFileName = uuid.toString()+"_" +strUploadFileName;
			System.out.println("UUID처리된파일이름: "+strUploadFileName);
			
			//최종 저장 정보를 가진 파일 객체(저장경로와 파일이름 문자열)
			File saveUploadFile=new File(strUploadFileRepoDir, strUploadFileName);
			System.out.println("저장시 사용되는 파일이름(saveUploadFile, 경로포함): "+ saveUploadFile);
			
			try {
				//서버에 파일객체를 이용하여 업로드 파일 저장
				multipartUploadFile.transferTo(saveUploadFile);
				
				//이미지 파일 여부 확인 후, 썸네일 이미지 생성
				if(checkIsImageForUploadFile(saveUploadFile)) {//이미지 파일인 경우
					//썸네일 생성경로와 파일이름이 설정된 파일객체를 전송 보내는 FileOutputStream 객체 생성
					FileOutputStream outputStreamForThumbnail =
							new FileOutputStream(
									new File(strUploadFileRepoDir, "s_"+ strUploadFileName));
					
					//FileOutputStream으로 보내진 파일객체를 서버에 저장(input)하여 100x100크기(px)의 썸네일 생성
					Thumbnailator.createThumbnail(multipartUploadFile.getInputStream(),
											outputStreamForThumbnail, 20, 20);
					
					//OUT 스트림리소스 닫기
					outputStreamForThumbnail.close();
				}
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}//End-for
	}
}
