/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.VendingMachinePersistenceException;
import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.service.EmptyInventoryException;
import com.sg.vendingmachinespringmvc.service.InsufficientFundsException;
import javax.inject.Inject;

import com.sg.vendingmachinespringmvc.service.ServiceLayer;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author tjsut
 */
@Controller
public class VendingMachineController {
    
    BigDecimal userBalance = BigDecimal.ZERO;
    Integer itemId = null;
    String messageBox = "";
    String changeDisplay = "";
    Change change = new Change();

    @Inject
    ServiceLayer service;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String loadMachine(Model model) throws VendingMachinePersistenceException {
        List<Item> snackList = service.getAllItems();
        model.addAttribute("snackList", snackList);
        model.addAttribute("userBalance", userBalance);
        model.addAttribute("itemId", itemId);
        model.addAttribute("messageBox", messageBox);
        model.addAttribute("changeDisplay", changeDisplay);
        return "index";
    }
    
    @PostMapping("addDollar")
    public String addDollar() {
        userBalance = service.insertUserMoney(new BigDecimal("1.00"));
        messageBox = "";
        changeDisplay = "";
        return "redirect:/";
    }
    
    @PostMapping("addQuarter")
    public String addQuarter() {
        userBalance = service.insertUserMoney(new BigDecimal("0.25"));
        messageBox = "";
        changeDisplay = "";
        return "redirect:/";
    }
    
    @PostMapping("addDime")
    public String addDime() {
        userBalance = service.insertUserMoney(new BigDecimal("0.10"));
        messageBox = "";
        changeDisplay = "";
        return "redirect:/";
    }
    
    @PostMapping("addNickel")
    public String addNickel() {
        userBalance = service.insertUserMoney(new BigDecimal("0.05"));
        messageBox = "";
        changeDisplay = "";
        return "redirect:/";
    }
    
    @PostMapping("getItemId")
    public String getItemId(Integer num) {
        itemId = num;
        return "redirect:/";
    }
    
    @PostMapping("purchaseItem")
    public String purchaseItem() throws VendingMachinePersistenceException, EmptyInventoryException, InsufficientFundsException {
        if (itemId == null) {
            messageBox = "Please choose an item.";
            return "redirect:/";
        }
        
        if (service.getItemById(String.valueOf(itemId)).getInventory() == 0) {
            messageBox = "Item is currently unavailable. Choose another.";
            return "redirect:/";
        }
        
        try {
            Item item = service.getItemById(String.valueOf(itemId));
            BigDecimal remainingBalance = service.purchaseItem(item, userBalance);
            service.calculateChange(remainingBalance);
            Change userChange = service.returnChange();
        
            messageBox = "Come back soon!";
            userBalance = BigDecimal.ZERO;
            service.resetUserBalance();
            changeDisplay = userChange.numDollars + " dollars " 
                + userChange.numQuarters + " quarters " + userChange.numDimes
                + " dimes " + userChange.numNickels + " nickels";
        } catch (InsufficientFundsException ex) {
            messageBox = "You do not have enough money to buy this item.";
        }

        return "redirect:/";
        
    }
    
    @PostMapping("changeReturn")
    public String changeReturn() {
        itemId = null;
        if (userBalance.equals(BigDecimal.ZERO)) {
            messageBox = "No money has been deposited.";
        }
        
        service.calculateChange(userBalance);
        Change userChange = service.returnChange();
        userBalance = BigDecimal.ZERO;
        service.resetUserBalance();
        changeDisplay = userChange.numDollars + " dollars " 
                + userChange.numQuarters + " quarters " + userChange.numDimes
                + " dimes " + userChange.numNickels + " nickels";
        
        return "redirect:/";
    }
    
}
