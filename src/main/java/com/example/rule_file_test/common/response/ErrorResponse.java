package com.example.rule_file_test.common.response;

import java.time.LocalDateTime;

import com.example.rule_file_test.common.exception.ErrorCode;

public class ErrorResponse {

	private final boolean success;
	private final LocalDateTime responseTime;
	private final int code;
	private final String message;

	private ErrorResponse(ErrorCode errorCode) {
		this.success = false;
		this.responseTime = LocalDateTime.now();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public boolean isSuccess() {
		return success;
	}

	public LocalDateTime getResponseTime() {
		return responseTime;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
