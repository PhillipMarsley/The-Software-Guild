/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Dao.DaoException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Audit {

    public static final String AUDIT_FILE = "audit.txt";

    public void writeAuditEntry(String entry) throws DaoException {
	PrintWriter out;

	try {
	    out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
	}
	catch (IOException e) {
	    throw new DaoException("Could not persist audit information.", e);
	}

	/*
	timestamp
	item that was requested
	type of error (insufficient funds or no inventory)
	 */
	LocalDateTime timestamp = LocalDateTime.now();
	out.println(timestamp.toString() + " : " + entry);
	out.flush();
    }

}
