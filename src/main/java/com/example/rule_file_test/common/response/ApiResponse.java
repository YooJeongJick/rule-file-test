package com.example.rule_file_test.common.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	private final boolean success;
	private final LocalDateTime responseTime;
	private final T data;

	private ApiResponse(boolean success, T data) {
		this.success = success;
		this.responseTime = LocalDateTime.now();
		this.data = data;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data);
	}

	public boolean isSuccess() {
		return success;
	}

	public LocalDateTime getResponseTime() {
		return responseTime;
	}

	public T getData() {
		return data;
	}

}
