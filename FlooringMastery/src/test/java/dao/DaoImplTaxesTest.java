/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.State;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dan
 */
public class DaoImplTaxesTest {

    private DaoImplTaxes dit = new DaoImplTaxes();
    private int size = dit.states.size();
    
    private State state1;
    private State state2;
    private State state3;

    @Before
    public void setUp() {
	state1 = new State();
	state1.setName("AA");
	state1.setTaxRate(new BigDecimal("1.11"));
	
	state2 = new State();
	state2.setName("BB");
	state2.setTaxRate(new BigDecimal("2.22"));
	
	state3 = new State();
	state3.setName("CC");
	state3.setTaxRate(new BigDecimal("3.33"));

	dit.states.add(state1);
	dit.states.add(state2);
	dit.states.add(state3);
    }

    @After
    public void tearDown() {
	dit.states.remove(state1);
	dit.states.remove(state2);
	dit.states.remove(state3);
    }

    @Test
    public void testGetState() {
	try {
	    assertEquals(state1, dit.getState("AA"));
	    assertEquals(state2, dit.getState("BB"));
	    assertEquals(state3, dit.getState("CC"));
	    //pass
	}
	catch (DaoPersistanceException ex) {
	    fail(ex.getMessage());
	}
    }

    @Test
    public void testBadInput() {
	try {
	    assertNull(dit.getState("ABCD"));
	    fail();
	}
	catch (DaoPersistanceException ex) {
	    //pass
	}
    }

}
