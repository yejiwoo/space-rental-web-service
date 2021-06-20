package com.rentspace.pro.common.sms;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class SendSmsResponseVO {
	private String requestId;
	private Timestamp requestTime;
	private String statusCode;
	private String statusName;

}
