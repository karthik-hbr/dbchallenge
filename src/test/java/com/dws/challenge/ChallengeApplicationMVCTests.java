package com.dws.challenge;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ChallengeApplicationMVCTests {

	private MockMvc mockMvc;
	ObjectMapper om = new ObjectMapper();
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void createAccountTest() throws Exception {
		Account account = new Account("Acc12345",new BigDecimal(121212));
		String accountJsonRequest = om.writeValueAsString(account);
		MvcResult result = mockMvc.perform(post("/v1/accounts").content(accountJsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		assertEquals(201,result.getResponse().getStatus());
	}

	@Test
	void initiateTransferTest() throws Exception{
		Account accountSender = new Account("Acc12344",new BigDecimal(12000));
		Account accountReceiver = new Account("Acc12345",new BigDecimal(121212));

		mockMvc.perform(post("/v1/accounts").content(om.writeValueAsString(accountSender))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		mockMvc.perform(post("/v1/accounts").content(om.writeValueAsString(accountReceiver))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

		TransferDTO transferDTO = new TransferDTO("Acc12345","Acc12344",new BigDecimal(3333));
		MvcResult result = mockMvc.perform(post("/v1/transfer/initiateTransfer").content(om.writeValueAsString(transferDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isAccepted()).andReturn();
		assertAll(
				() -> assertEquals(202,result.getResponse().getStatus()),
				() -> assertEquals("The Transfer is complete",result.getResponse().getContentAsString())
		);
	}
}
