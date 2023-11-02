package com.dws.challenge;

import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.AccountsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ChallengeApplicationServiceTests {

    @MockBean
    private AccountsService accountsService;

    @MockBean
    private AccountsRepository accountsRepository;

    @Test
    void getAccountTest(){
        String accountId = "Acc12345";
        when(accountsRepository.getAccount(accountId)).thenReturn(new Account("Acc12345",new BigDecimal(121212)));
        assertEquals(accountId, accountsService.getAccount(accountId).getAccountId());
    }
}
