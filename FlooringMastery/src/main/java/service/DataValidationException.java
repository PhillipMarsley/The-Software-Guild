/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Dan
 */
public class DataValidationException extends Throwable {

    public DataValidationException() {
    }

    public DataValidationException(String message) {
	super(message);
    }

    public DataValidationException(String message, Throwable cause) {
	super(message, cause);
    }

    public DataValidationException(Throwable cause) {
	super(cause);
    }

    public DataValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
