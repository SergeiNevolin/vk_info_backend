package com.vkinfo.vk_person_info;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class VkPersonInfoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String token = "your_token";

	@Test
	public void infoRequestOkIsMember() throws Exception {
		String userRequest = "{\"user_id\": 175002646, \"group_id\": 104061344}";
		
		this.mockMvc.perform(post("/api/v1/vk/info")
			.header("vk_service_token", token)
			.contentType(MediaType.APPLICATION_JSON)
            .content(userRequest))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.last_name").value("Nevolin"))
			.andExpect(jsonPath("$.first_name").value("Sergey"))
			.andExpect(jsonPath("$.member").value("true"));
	}

	@Test
	public void infoRequestOkIsNotMember() throws Exception {
		String userRequest = "{\"user_id\": 175002646, \"group_id\": 10406134}";
		
		this.mockMvc.perform(post("/api/v1/vk/info")
			.header("vk_service_token", token)
			.contentType(MediaType.APPLICATION_JSON)
            .content(userRequest))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.last_name").value("Nevolin"))
			.andExpect(jsonPath("$.first_name").value("Sergey"))
			.andExpect(jsonPath("$.member").value("false"));
	}

	@Test
	public void infoRequestTokenError() throws Exception {
		String userRequest = "{\"user_id\": 175002646, \"group_id\": 10406134}";
		String mistakeToken = "123456";
		
		this.mockMvc.perform(post("/api/v1/vk/info")
			.header("vk_service_token", mistakeToken)
			.contentType(MediaType.APPLICATION_JSON)
            .content(userRequest))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void infoRequestValidationErrorParamBeyond() throws Exception {
		String userRequest = "{\"user_id\": -1, \"group_id\": 10406134}";
		
		this.mockMvc.perform(post("/api/v1/vk/info")
			.header("vk_service_token", token)
			.contentType(MediaType.APPLICATION_JSON)
            .content(userRequest))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void infoRequestValidationErrorMissParam() throws Exception {
		String userRequest = "{\"group_id\": 10406134}";
		
		this.mockMvc.perform(post("/api/v1/vk/info")
			.header("vk_service_token", token)
			.contentType(MediaType.APPLICATION_JSON)
            .content(userRequest))
			.andExpect(status().isBadRequest());
	}

}
