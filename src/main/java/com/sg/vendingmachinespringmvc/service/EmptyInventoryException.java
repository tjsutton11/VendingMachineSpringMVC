/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

/**
 *
 * @author tjsut
 */
public class EmptyInventoryException extends Exception {
    
    public EmptyInventoryException(String message) {
        super(message);
    }
    
    public EmptyInventoryException (String message, Throwable cause) {
        super(message, cause);
    }
    
}
