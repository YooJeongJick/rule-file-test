package com.example.rule_file_test.todo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.rule_file_test.common.exception.BusinessException;
import com.example.rule_file_test.common.exception.ErrorCode;
import com.example.rule_file_test.todo.domain.Todo;
import com.example.rule_file_test.todo.dto.request.TodoCreateRequestDto;
import com.example.rule_file_test.todo.dto.request.TodoUpdateCompletedRequestDto;
import com.example.rule_file_test.todo.dto.response.TodoResponseDto;
import com.example.rule_file_test.todo.repository.TodoRepository;

@Service
public class TodoService {

	private static final Logger log = LoggerFactory.getLogger(TodoService.class);

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public TodoResponseDto createTodo(TodoCreateRequestDto requestDto) {
		String title = validateTitle(requestDto.getTitle());
		Todo todo = todoRepository.save(title);
		log.info("할 일 등록 완료. todoId={}", todo.getTodoId());
		return TodoResponseDto.from(todo);
	}

	public List<TodoResponseDto> getTodos() {
		return todoRepository.findAll().stream()
				.map(TodoResponseDto::from)
				.toList();
	}

	public TodoResponseDto updateCompleted(Long todoId, TodoUpdateCompletedRequestDto requestDto) {
		if (requestDto.getCompleted() == null) {
			throw new BusinessException(ErrorCode.TODO_COMPLETED_REQUIRED);
		}
		Todo todo = todoRepository.findById(todoId)
				.orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));
		todo.changeCompleted(requestDto.getCompleted());
		log.info("할 일 완료 상태 변경. todoId={}, completed={}", todoId, requestDto.getCompleted());
		return TodoResponseDto.from(todo);
	}

	public void deleteTodo(Long todoId) {
		todoRepository.findById(todoId)
				.orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));
		todoRepository.deleteById(todoId);
		log.info("할 일 삭제 완료. todoId={}", todoId);
	}

	private String validateTitle(String title) {
		if (title == null || title.isBlank()) {
			throw new BusinessException(ErrorCode.TODO_TITLE_REQUIRED);
		}
		return title.trim();
	}

}
