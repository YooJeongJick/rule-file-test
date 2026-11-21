package com.example.rule_file_test.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rule_file_test.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		log.warn("비즈니스 예외 발생. code={}, message={}", errorCode.getCode(), errorCode.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponse.of(errorCode));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
		log.warn("요청 본문 파싱 실패. message={}", exception.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponse.of(errorCode));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("서버 내부 오류 발생", exception);
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponse.of(errorCode));
	}

}
