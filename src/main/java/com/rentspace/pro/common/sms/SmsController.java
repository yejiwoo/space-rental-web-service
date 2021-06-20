package com.rentspace.pro.common.sms;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@Controller
public class SmsController {
	
	private String accessKey="l0om2Gw1XBvN7rLQHhuq";
	private String serviceId="ncp:sms:kr:268731585969:rentspace";
	private String secretKey="fZYqoWA1mfa84XcQhXHkKJlJYvh0LkmYYSEFoRvm";
	private String sendFrom="01066205323";

	
	@ResponseBody
	@PostMapping("/sendSms2")
	public void sendSms2(String recipientPhoneNumber, String content) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {

		String hostNameUrl="https://sens.apigw.ntruss.com";
		String requestUrl="/sms/v2/services/"+serviceId+"/messages";
		String apiUrl=hostNameUrl+requestUrl;
		
		String method="POST";
		String timestamp = Long.toString(System.currentTimeMillis());
		
		JSONObject bodyJson = new JSONObject();
		JSONObject toJson = new JSONObject();
		JSONArray toArr = new JSONArray();
		
		toJson.put("subject", "");
		toJson.put("content", content);
		toJson.put("to", recipientPhoneNumber);
		toArr.put(toJson);
		
		bodyJson.put("type", "SMS");
		bodyJson.put("contentType", "COMM");
		bodyJson.put("countryCode", "82");
		bodyJson.put("from", sendFrom);
		bodyJson.put("subject", "1");
		bodyJson.put("content", "2");
		bodyJson.put("messages", toArr);

		String body=bodyJson.toString();
		System.out.println(body);
		
		try {
			URL url=new URL(apiUrl);
			
			HttpURLConnection con=(HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
			con.setRequestProperty("x-ncp-iam-access-key", accessKey);
			con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(timestamp));
			con.setRequestMethod(method);
			con.setDoOutput(true);
			System.out.println(con.getContent());
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			
			wr.write(body.getBytes());
			wr.flush();
			wr.close();
			
			int responseCode=con.getResponseCode();
			BufferedReader br;
			System.out.println("responseCode "+responseCode);
			if(responseCode==202) br=new BufferedReader(new InputStreamReader(con.getInputStream()));
			else br=new BufferedReader(new InputStreamReader(con.getErrorStream()));
			
			String inputLine;
			StringBuffer response=new StringBuffer();
			while((inputLine = br.readLine())!=null) {
				response.append(inputLine);
			}
			br.close();
			
			System.out.println(response.toString());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@ResponseBody
	@PostMapping("/sendSms")
	public Map<String,String> sendSms(String recipientPhoneNumber, String content) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {

		System.out.println("------------sendSms-------------");
//		Long time = new DateCreator().getTimestamp().getTime(); 
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time=sdf.format(timestamp);
		System.out.println("--------------time 생성 완료---------------");
		
		List<MessagesRequestVO> messages = new ArrayList<>(); 
		// 보내는 사람에게 내용을 보냄. 
		messages.add(new MessagesRequestVO(recipientPhoneNumber,content)); // content부분이 내용임 
		System.out.println("---------------messages 완료---------------");
		
		// 전체 json에 대해 메시지를 만든다. 
		SmsRequestVO smsRequestVO = new SmsRequestVO("SMS", "COMM", "82", sendFrom, "MangoLtd", messages); 
		// 쌓아온 바디를 json 형태로 변환시켜준다. 
		ObjectMapper objectMapper = new ObjectMapper(); 
		String jsonBody = objectMapper.writeValueAsString(smsRequestVO); 
		
		// 헤더에서 여러 설정값들을 잡아준다. 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.set("x-ncp-apigw-timestamp", time); 
		headers.set("x-ncp-iam-access-key", accessKey); 
		
		// 제일 중요한 signature 서명하기. 
		String sig = makeSignature(time); 
		System.out.println("sig -> " + sig); 
		headers.set("x-ncp-apigw-signature-v2", sig); 
		
		// 위에서 조립한 jsonBody와 헤더를 조립한다. 
		HttpEntity<String> request = new HttpEntity<>(jsonBody, headers); 
		System.out.println("getHeaders(): "+request.getHeaders()); 
		System.out.println("getBody(): "+request.getBody()); 
		
		// restTemplate로 post 요청을 보낸다. 별 일 없으면 202 코드 반환된다. 
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	
		System.out.println("RestTemplate 생성 완료");
		SendSmsResponseVO sendSmsResponseVO = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+serviceId+"/messages"), request, SendSmsResponseVO.class); 
		System.out.println("statusCode: " +sendSmsResponseVO.getStatusCode()); 
		
//		return sendSmsResponseVO;
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("requestId", sendSmsResponseVO.getRequestId());
		map.put("requestTime", sendSmsResponseVO.getRequestTime().toString());
		map.put("statusCode", sendSmsResponseVO.getStatusCode());
		map.put("statusName", sendSmsResponseVO.getStatusName());

		return map;

	}
	
	public String makeSignature(String timestamp) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException { 
		String space = " "; // one space 
		String newLine = "\n"; // new line 
		String method = "POST"; // method 
		String url = "/sms/v2/services/"+serviceId+"/messages"; // url (include query string) 
//		String timestamp = time; // current timestamp (epoch) 
//		String accessKey = accessKey; // access key id (from portal or Sub Account) 
//		String secretKey = secretKey; 
		String message = new StringBuilder() 
				.append(method) 
				.append(space) 
				.append(url) 
				.append(newLine) 
				.append(timestamp) 
				.append(newLine) 
				.append(accessKey) 
				.toString(); 
		
		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256"); 
		Mac mac = Mac.getInstance("HmacSHA256"); 
		mac.init(signingKey); 
		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8")); 

		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
		
//		SecretKeySpec signingKey;
//		String encodeBase64String;
//		try {
//			signingKey=new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//			Mac mac = Mac.getInstance("HmacSHA256");
//			mac.init(signingKey);
//			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//			encodeBase64String=Base64.getEncoder().encodeToString(rawHmac);
//			
//		}catch(UnsupportedEncodingException e) {
//			encodeBase64String = e.toString();
//		}
		
		return encodeBase64String; 
	}
	
}


