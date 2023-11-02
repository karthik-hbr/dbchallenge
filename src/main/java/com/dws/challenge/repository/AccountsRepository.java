package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferDTO;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.TransferException;

public interface AccountsRepository {

    void createAccount(Account account) throws DuplicateAccountIdException;

    void startTransfer(TransferDTO transferDTO) throws TransferException;

    Account getAccount(String accountId);

    void clearAccounts();

}
