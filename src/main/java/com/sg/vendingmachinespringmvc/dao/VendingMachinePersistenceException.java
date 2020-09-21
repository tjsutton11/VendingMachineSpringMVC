package com.sg.vendingmachinespringmvc.dao;

public class VendingMachinePersistenceException extends Exception {

    private static final long serialVersionUID = 1L;

    public VendingMachinePersistenceException(String message) {
        super(message);
    }
    
    public VendingMachinePersistenceException (String message, Throwable cause) {
        super(message, cause);
    }
    
}