package com.dws.challenge.web;

import com.dws.challenge.domain.TransferDTO;
import com.dws.challenge.exception.TransferException;
import com.dws.challenge.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transfer")
@Slf4j
public class TransferController {

    private final AccountsService accountsService;

    @Autowired
    public TransferController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping(path = "/initiateTransfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> initiateTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        log.info("Initiating Transfer of amount {} from {} to {}!", transferDTO.getAmountToBeTransferred(), transferDTO.getAccountIdFrom(), transferDTO.getAccountIdTo());
        try {
            accountsService.startTransfer(transferDTO);
        } catch (TransferException te) {
            return new ResponseEntity<>(te.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Transfer of amount {} from {} to {} is complete!", transferDTO.getAmountToBeTransferred(), transferDTO.getAccountIdFrom(), transferDTO.getAccountIdTo());
        return ResponseEntity.accepted().body("The Transfer is complete");
    }
}
