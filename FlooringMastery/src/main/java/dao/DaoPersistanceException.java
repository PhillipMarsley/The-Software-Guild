/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Dan
 */
public class DaoPersistanceException extends Throwable{

    public DaoPersistanceException() {
    }

    public DaoPersistanceException(String message) {
	super(message);
    }

    public DaoPersistanceException(String message, Throwable cause) {
	super(message, cause);
    }

    public DaoPersistanceException(Throwable cause) {
	super(cause);
    }

    public DaoPersistanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
