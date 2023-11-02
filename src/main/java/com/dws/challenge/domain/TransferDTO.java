package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferDTO {

    @NotNull
    @NotEmpty
    private final String accountIdFrom;

    @NotNull
    @NotEmpty
    private final String accountIdTo;

    @NotNull
    @Min(value = 1, message = "Initial balance must be positive.")
    private final BigDecimal amountToBeTransferred;

    @JsonCreator
    public TransferDTO(@JsonProperty("accountIdFrom") String accountIdFrom, @JsonProperty("accountIdTo") String accountIdTo, @JsonProperty("amountToBeTransferred") BigDecimal amountToBeTransferred) {
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amountToBeTransferred = amountToBeTransferred;
    }

}
