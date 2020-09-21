/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import java.util.List;

import com.sg.vendingmachinespringmvc.model.Item;

/**
 *
 * @author tjsut
 */
public interface VendingMachineDao {

    Item getItem(String itemId) throws VendingMachinePersistenceException;

    List<Item> getAvailableItems() throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item updateInventory(Item item) throws VendingMachinePersistenceException;
    
}
