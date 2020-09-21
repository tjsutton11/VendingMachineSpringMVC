/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingMachinePersistenceException;
import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author tjsut
 */
public interface ServiceLayer {
    
    public Item getItemById (String itemId) throws VendingMachinePersistenceException;
    
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException;
    
    public List<Item> getAllItems() throws VendingMachinePersistenceException;
    
    public BigDecimal purchaseItem(Item item, BigDecimal userBalance) throws VendingMachinePersistenceException, EmptyInventoryException, InsufficientFundsException;
    
    public BigDecimal insertUserMoney(BigDecimal money);
    
    public BigDecimal getUserBalance();
    
    public BigDecimal resetUserBalance();
    
    public void calculateChange(BigDecimal userBalance);
    
    public Change returnChange();
    
}
