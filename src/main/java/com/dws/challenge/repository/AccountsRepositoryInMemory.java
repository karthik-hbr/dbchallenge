package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferDTO;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.TransferException;
import com.dws.challenge.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    @Autowired
    private final NotificationService notificationService;

    public AccountsRepositoryInMemory(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void createAccount(Account account) throws DuplicateAccountIdException {
        Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (previousAccount != null) {
            throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!!!");
        }
    }

    @Override
    public void startTransfer(TransferDTO transferDTO) throws TransferException {
        Account accountSender = accounts.get(transferDTO.getAccountIdFrom());
        if(accountSender == null){
            throw new TransferException("Account id " + transferDTO.getAccountIdFrom() + " does not exists!!!");
        }
        Account accountReceiver = accounts.get(transferDTO.getAccountIdTo());
        if(accountReceiver == null){
            throw new TransferException("Receiver Account id " + transferDTO.getAccountIdTo() + " does not exists!!!");
        }
        if(!(accountSender.getBalance().compareTo(transferDTO.getAmountToBeTransferred()) >= 0)){
            throw new TransferException("Account Balance is " + accountSender.getBalance() + ". Cannot withdraw " + transferDTO.getAmountToBeTransferred() +"!!!");
        }
        accountSender.setBalance(accountSender.getBalance().subtract(transferDTO.getAmountToBeTransferred()));
        accounts.put(accountSender.getAccountId(), accountSender);
        notificationService.notifyAboutTransfer(accountSender, "Amount " + transferDTO.getAmountToBeTransferred() + " is transferred to "+transferDTO.getAccountIdTo());
        accountReceiver.setBalance(accountReceiver.getBalance().add(transferDTO.getAmountToBeTransferred()));
        accounts.put(accountReceiver.getAccountId(), accountReceiver);
        notificationService.notifyAboutTransfer(accountReceiver, "Amount " + transferDTO.getAmountToBeTransferred() + "is credited by "+transferDTO.getAccountIdFrom());
    }

    @Override
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void clearAccounts() {
        accounts.clear();
    }

}
