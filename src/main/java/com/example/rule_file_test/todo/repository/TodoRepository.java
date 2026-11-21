package com.example.rule_file_test.todo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.rule_file_test.todo.domain.Todo;

@Repository
public class TodoRepository {

	private final ConcurrentHashMap<Long, Todo> store = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(0L);

	public Todo save(String title) {
		Long todoId = sequence.incrementAndGet();
		Todo todo = new Todo(todoId, title, false);
		store.put(todoId, todo);
		return todo;
	}

	public List<Todo> findAll() {
		return new ArrayList<>(store.values());
	}

	public Optional<Todo> findById(Long todoId) {
		return Optional.ofNullable(store.get(todoId));
	}

	public void deleteById(Long todoId) {
		store.remove(todoId);
	}

}
