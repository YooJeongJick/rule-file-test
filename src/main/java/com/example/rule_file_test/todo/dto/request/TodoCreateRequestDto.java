package com.example.rule_file_test.todo.dto.request;

public class TodoCreateRequestDto {

	private String title;

	public TodoCreateRequestDto() {
	}

	public TodoCreateRequestDto(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
