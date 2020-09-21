/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingMachineAuditDao;
import com.sg.vendingmachinespringmvc.dao.VendingMachineDao;
import com.sg.vendingmachinespringmvc.dao.VendingMachinePersistenceException;
import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author tjsut
 */
public class ServiceLayerImpl implements ServiceLayer {
    
    VendingMachineDao dao;
    VendingMachineAuditDao audit;
    Change change = new Change();
    BigDecimal userBalance = new BigDecimal("0");
    int numDollars, numQuarters, numDimes, numNickels, numPennies;
    
    public ServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao audit) {
        this.dao = dao;
        this.audit = audit;
    }

    @Override
    public Item getItemById(String itemId) throws VendingMachinePersistenceException {
        return dao.getItem(itemId);
    }

    @Override
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        return dao.getAvailableItems();
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public BigDecimal purchaseItem(Item item, BigDecimal amount) throws VendingMachinePersistenceException, EmptyInventoryException, InsufficientFundsException  {
        if (amount.floatValue() >= item.getPrice().floatValue()) {
            if (item.getInventory() > 0) {
                item.setInventory(item.getInventory() - 1);
                dao.updateInventory(item);
                userBalance = userBalance.subtract(item.getPrice());
                
                try {
                    audit.writeAuditEntry("Item Purchased");
                } catch (VendingMachinePersistenceException ex) {
                    throw new VendingMachinePersistenceException("Audit log could not be persisted.", ex);
                }
                return amount.subtract(item.getPrice());
            } else {
                throw new EmptyInventoryException("This item currently unavailable.");
            }
        } else {
            throw new InsufficientFundsException("You do not have enough money to buy this item.");
        }
    }

    @Override
    public BigDecimal insertUserMoney(BigDecimal money) {
        userBalance = userBalance.add(money);
        return userBalance;
    }

    @Override
    public BigDecimal getUserBalance() {
        return userBalance;
    }
    
    @Override
    public BigDecimal resetUserBalance() {
        change.numDollars = 0;
        change.numQuarters = 0;
        change.numDimes = 0;
        change.numNickels = 0;
        change.numPennies = 0;
        return userBalance = new BigDecimal("0");
    }

    @Override
    public void calculateChange(BigDecimal userBalance) {
        
        while (userBalance.floatValue() > 0) {
            if (userBalance.floatValue() >= 1) {
                userBalance = userBalance.subtract(change.DOLLARS);
                change.numDollars++;
            } else if (userBalance.floatValue() >= .25) {
                userBalance = userBalance.subtract(change.QUARTERS);
                change.numQuarters++;
            } else if (userBalance.floatValue() >= .10) {
                userBalance = userBalance.subtract(change.DIMES);
                change.numDimes++;
            } else if (userBalance.floatValue() >= .05) {
                userBalance = userBalance.subtract(change.NICKELS);
                change.numNickels++;
            } else if (userBalance.floatValue() >= .01) {
                userBalance = userBalance.subtract(change.PENNIES);
                change.numPennies++;
            }
        }
    }
    
    @Override
    public Change returnChange() {
        Change userChange = new Change();
        userChange.numDollars = change.getNumDollars();
        userChange.numQuarters = change.getNumQuarters();
        userChange.numDimes = change.getNumDimes();
        userChange.numNickels = change.getNumNickels();
        userChange.numPennies = change.getNumPennies();
        return userChange;
    }
    
}
