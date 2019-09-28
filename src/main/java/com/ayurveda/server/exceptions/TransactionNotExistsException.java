package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class TransactionNotExistsException extends ApiException {

    private static final TransactionNotExistsException TRANSACTION_NOT_EXISTS_EXCEPTION = new TransactionNotExistsException();

    public TransactionNotExistsException() {
        super(new ErrorResponse("TRXERR001",
                HttpStatus.NOT_FOUND.value(),
                "Transaction not exists",
                "The referring transaction does not exists"));
    }

    public static TransactionNotExistsException getInstance() {
        return TRANSACTION_NOT_EXISTS_EXCEPTION;
    }

}
