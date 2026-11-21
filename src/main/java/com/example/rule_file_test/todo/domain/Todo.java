package com.example.rule_file_test.todo.domain;

public class Todo {

	private Long todoId;
	private String title;
	private boolean completed;

	public Todo(Long todoId, String title, boolean completed) {
		this.todoId = todoId;
		this.title = title;
		this.completed = completed;
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

	public void changeCompleted(boolean completed) {
		this.completed = completed;
	}

}
