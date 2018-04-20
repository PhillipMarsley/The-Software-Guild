/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

/**
 *
 * @author Dan
 */
public class InsufficientFundsException extends Exception{

    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
	super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
	super(message, cause);
    }

    public InsufficientFundsException(Throwable cause) {
	super(cause);
    }

    public InsufficientFundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
