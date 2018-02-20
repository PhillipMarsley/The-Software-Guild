/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DaoImplOrders;
import dao.DaoImplProducts;
import dao.DaoImplTaxes;
import dao.DaoPersistanceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.Product;
import model.State;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dan
 */
public class ServiceTest {

    private DaoImplOrders dio = new DaoImplOrders();
    private DaoImplProducts dip = new DaoImplProducts();
    private DaoImplTaxes dit = new DaoImplTaxes();
    private String mode = "Production";
    private Service service = new Service(dio, dip, dit, mode);

    private List<Order> orders = new ArrayList<>();

    private Order order1 = new Order();
    private Order order2 = new Order();

    private int orderNumber1 = 8888;
    private int orderNumber2 = 9999;

    public ServiceTest() {
    }

    @Before
    public void setUp() {
	State state1 = new State();
	Product product1 = new Product();

	order1.setState(state1);
	order1.setProduct(product1);

	order1.setOrderNumber(1001);
	order1.setCustomerName("Bob");
	order1.setArea(new BigDecimal("10"));
	order1.getState().setName("IN");
	order1.getProduct().setType("Wood");

	State state2 = new State();
	Product product2 = new Product();

	order2.setState(state2);
	order2.setProduct(product2);

	order2.setOrderNumber(1002);
	order2.setCustomerName("Bob");
	order2.setArea(new BigDecimal("123.456789"));
	order2.getState().setName("MI");
	order2.getProduct().setType("Tile");

	orders.add(order1);
	orders.add(order2);
    }

    @After
    public void tearDown() {
	orders.remove(order1);
	orders.remove(order2);
    }

    @Test
    public void testAddOrder() {
	try {
	    service.addOrder(orders.get(0));

	    //IN,6.00
	    assertEquals(new BigDecimal("6.00"), order1.getState().getTaxRate());

	    //Wood,5.15,4.75
	    assertEquals(new BigDecimal("5.15"), order1.getProduct().getCostPerSqFtMaterial());
	    assertEquals(new BigDecimal("4.75"), order1.getProduct().getCostPerSqFtLabor());

	    assertEquals(new BigDecimal("51.50"), order1.getTotalCostMaterial());
	    assertEquals(new BigDecimal("47.50"), order1.getTotalCostLabor());
	    assertEquals(new BigDecimal("5.940000"), order1.getTotalCostTax());
	    assertEquals(new BigDecimal("104.940000"), order1.getOrderTotal());

	    service.addOrder(orders.get(1));

	    //MI,5.75
	    assertEquals(new BigDecimal("5.75"), order2.getState().getTaxRate());

	    //Tile,3.50,4.15
	    assertEquals(new BigDecimal("3.50"), order2.getProduct().getCostPerSqFtMaterial());
	    assertEquals(new BigDecimal("4.15"), order2.getProduct().getCostPerSqFtLabor());

	    assertEquals(new BigDecimal("432.09876150"), order2.getTotalCostMaterial());
	    assertEquals(new BigDecimal("512.34567435"), order2.getTotalCostLabor());
	    assertEquals(new BigDecimal("54.305555061375"), order2.getTotalCostTax());
	    assertEquals(new BigDecimal("998.749990911375"), order2.getOrderTotal());

	    //pass
	}
	catch (DaoPersistanceException ex) {
	    System.out.println(ex.getMessage() + "\t" + ServiceTest.class.getName());
	    fail();
	}
    }

    //==========================================================================
    //		pass trough methods
    //	  V	already tested in DaoImplOrdersTest	V
    //==========================================================================
    @Test
    public void testRemoveOrder() {
    }

    @Test
    public void testGetOrder() {
    }

    @Test
    public void testGetOrders() {
    }

    @Test
    public void testSave() {
    }
}
