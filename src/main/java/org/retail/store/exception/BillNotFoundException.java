package org.retail.store.exception;

import org.retail.store.model.Bill;

public class BillNotFoundException extends RuntimeException{
    public BillNotFoundException(String message){
        super(message);
    }
}
