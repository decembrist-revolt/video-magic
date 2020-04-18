package org.decembrist.videomagic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decembrist.videomagic.dto.JwtBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@DisplayName("Check jwt authentication right token return")
	public void authenticationTest() throws Exception {
		final String jwtBodyString = mapper.writeValueAsString(new JwtBody("user", "user"));
		final MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType("application/json")
				.content(jwtBodyString);
		mockMvc.perform(request)
				.andExpect(status().isOk());
	}

}
