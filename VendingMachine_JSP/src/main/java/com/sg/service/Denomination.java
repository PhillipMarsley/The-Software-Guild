/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Dan
 */
public enum Denomination {
    PENNY(new BigDecimal("0.01")),
    NICKLE(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25")),
    LOONIE(new BigDecimal("1.00")),
    TOONIE(new BigDecimal("2.00")),
    FIVE_DOLLAR_BILL(new BigDecimal("5.00")),
    TEN_DOLLAR_BILL(new BigDecimal("10.00")),
    TWENTY_DOLLAR_BILL(new BigDecimal("20.00"));
    
    private BigDecimal value;

    private Denomination(BigDecimal value) {
	this.value = value;
    }
    
    public BigDecimal getValue() {
	return value;
    }
}
