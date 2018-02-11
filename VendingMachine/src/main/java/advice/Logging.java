/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advice;

import Dao.DaoException;
import Service.Audit;
import Service.Service;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Dan
 */
public class Logging {

    Audit audit;
    Service service;

    public Logging(Audit audit, Service service) {
	this.audit = audit;
	this.service = service;
    }

    /*
	timestamp
	item that was requested
	type of error (insufficient funds or no inventory)
     */
    public void createAuditEntry(JoinPoint jp, Throwable error) {
	Object[] args = jp.getArgs();
	String auditEntry = jp.getSignature().getName() + " : ";
	for (Object currentArg : args) {
	    if (currentArg instanceof Integer) {
		auditEntry += service.getItemName((int) currentArg) + " : ";
		break;
	    }
	}
	auditEntry += error.getClass().getName();

	try {
	    audit.writeAuditEntry(auditEntry);
	}
	catch (DaoException ex) {
	    System.err.println("ERROR: Could not create audit entry in Logging");
	}
    }

}
