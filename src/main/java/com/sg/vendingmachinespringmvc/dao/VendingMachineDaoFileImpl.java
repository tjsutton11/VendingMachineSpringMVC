package com.sg.vendingmachinespringmvc.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.sg.vendingmachinespringmvc.model.Item;

public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private LinkedHashMap<String, Item> inventoryList = new LinkedHashMap<>();
    private static final String INVENTORY = "inventory.txt";
    private static final String DELIMITER = "::";

    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException {
        readInventory();
        return inventoryList.get(itemId);

    }

    @Override
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        readInventory();
        return new ArrayList<Item>(inventoryList.values()
                .stream()
                .filter(i -> i.getInventory() > 0)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        readInventory();
        return new ArrayList<Item>(inventoryList.values());
    }

    @Override
    public Item updateInventory(Item item) throws VendingMachinePersistenceException {
        Item updatedItem = inventoryList.put(item.getItemId(), item);
        writeInventory();
        return updatedItem;
    }

    private void readInventory() throws VendingMachinePersistenceException {
        Scanner sc;
        
        try {
        sc = new Scanner(new BufferedReader(new FileReader(INVENTORY)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load inventory.", e);
        }
        
        String currentLine;
        String[] currentTokens;
        
        while(sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            
            Item currentItem = new Item(currentTokens[0]);
            currentItem.setName(currentTokens[1]);
            currentItem.setPrice(new BigDecimal(currentTokens[2]));
            currentItem.setInventory(Integer.parseInt(currentTokens[3]));
            
            inventoryList.put(currentItem.getItemId(), currentItem);
            
        }
        sc.close();  
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(INVENTORY));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save inventory.", e);
        }
        
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            out.println(currentItem.getItemId() + DELIMITER
                + currentItem.getName() + DELIMITER
                + currentItem.getPrice() + DELIMITER
                + currentItem.getInventory() + DELIMITER);
            out.flush();
        }
        out.close();
    }

}