/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dan
 */
public class DaoImplProductsTest {

    private DaoImplProducts dip = new DaoImplProducts();
    private int size = dip.products.size();

    private Product product1;
    private Product product2;
    private Product product3;

    @Before
    public void setUp() {

	product1 = new Product();
	product1.setType("wood");
	product1.setCostPerSqFtMaterial(new BigDecimal("1"));
	product1.setCostPerSqFtLabor(new BigDecimal("1.11"));

	product2 = new Product();
	product2.setType("stone");
	product2.setCostPerSqFtMaterial(new BigDecimal("2"));
	product2.setCostPerSqFtLabor(new BigDecimal("2.22"));

	product3 = new Product();
	product3.setType("sand");
	product3.setCostPerSqFtMaterial(new BigDecimal("3"));
	product3.setCostPerSqFtLabor(new BigDecimal("3.33"));

	dip.products.add(product1);
	dip.products.add(product2);
	dip.products.add(product3);
    }

    @After
    public void tearDown() {
	dip.products.remove(product1);
	dip.products.remove(product2);
	dip.products.remove(product3);
    }

    @Test
    public void testGetProduct() {
	try {
	    assertEquals(product1, dip.getProduct("wood"));
	    assertEquals(product2, dip.getProduct("stone"));
	    assertEquals(product3, dip.getProduct("sand"));
	    //pass
	}
	catch (DaoPersistanceException ex) {
	    fail(ex.getMessage());
	}
    }

    @Test
    public void testBadInput() {
	try {
	    assertNull(dip.getProduct("notAProduct"));
	    fail();
	}
	catch (DaoPersistanceException ex) {
	    //pass
	}
    }
}
