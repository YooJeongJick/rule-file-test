package com.example.rule_file_test.todo.dto.response;

import com.example.rule_file_test.todo.domain.Todo;

public class TodoResponseDto {

	private Long todoId;
	private String title;
	private boolean completed;

	public TodoResponseDto(Long todoId, String title, boolean completed) {
		this.todoId = todoId;
		this.title = title;
		this.completed = completed;
	}

	public static TodoResponseDto from(Todo todo) {
		return new TodoResponseDto(todo.getTodoId(), todo.getTitle(), todo.isCompleted());
	}

	public Long getTodoId() {
		return todoId;
	}

	public String getTitle() {
		return title;
	}

	public boolean isCompleted() {
		return completed;
	}

}
