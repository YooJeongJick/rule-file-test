package com.example.rule_file_test.todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Order(1)
	@DisplayName("할 일을 등록한다")
	void createTodo() throws Exception {
		mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"공부하기\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.title").value("공부하기"))
				.andExpect(jsonPath("$.data.completed").value(false))
				.andExpect(jsonPath("$.responseTime").exists());
	}

	@Test
	@Order(2)
	@DisplayName("빈 문자열 할 일 등록을 거절한다")
	void createTodoWithBlankTitle() throws Exception {
		mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"   \"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.code").value(4001))
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	@Order(3)
	@DisplayName("할 일 목록을 조회한다")
	void getTodos() throws Exception {
		mockMvc.perform(get("/api/v1/todos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data[0].completed").exists());
	}

	@Test
	@Order(4)
	@DisplayName("할 일 완료 상태를 변경한다")
	void updateCompleted() throws Exception {
		mockMvc.perform(patch("/api/v1/todos/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"completed\":true}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.completed").value(true));
	}

	@Test
	@Order(5)
	@DisplayName("할 일을 삭제한다")
	void deleteTodo() throws Exception {
		mockMvc.perform(delete("/api/v1/todos/1"))
				.andExpect(status().isNoContent());
	}

}
