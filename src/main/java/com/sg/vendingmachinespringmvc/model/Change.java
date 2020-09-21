package com.sg.vendingmachinespringmvc.model;

import java.math.BigDecimal;

public class Change {

    public static final BigDecimal PENNIES = new BigDecimal("0.01");
    public static final BigDecimal NICKELS = new BigDecimal("0.05");
    public static final BigDecimal DIMES = new BigDecimal("0.10");
    public static final BigDecimal QUARTERS = new BigDecimal("0.25");
    public static final BigDecimal DOLLARS = new BigDecimal("1.00");
    
    public int numDollars = 0;
    public int numQuarters = 0;
    public int numDimes = 0;
    public int numNickels = 0;
    public int numPennies = 0; 

    public static BigDecimal getPENNIES() {
        return PENNIES;
    }

    public static BigDecimal getNICKELS() {
        return NICKELS;
    }

    public static BigDecimal getDIMES() {
        return DIMES;
    }

    public static BigDecimal getQUARTERS() {
        return QUARTERS;
    }

    public static BigDecimal getDOLLARS() {
        return DOLLARS;
    }

    public int getNumDollars() {
        return numDollars;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public int getNumPennies() {
        return numPennies;
    }

}