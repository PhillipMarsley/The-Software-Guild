/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author Dan
 */
public class NoItemInventoryException extends Exception{

    public NoItemInventoryException() {
    }

    public NoItemInventoryException(String message) {
	super(message);
    }

    public NoItemInventoryException(String message, Throwable cause) {
	super(message, cause);
    }

    public NoItemInventoryException(Throwable cause) {
	super(cause);
    }

    public NoItemInventoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
