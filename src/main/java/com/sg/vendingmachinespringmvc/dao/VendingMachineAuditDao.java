/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

/**
 *
 * @author tjsut
 */
public interface VendingMachineAuditDao {
    
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
    
}
