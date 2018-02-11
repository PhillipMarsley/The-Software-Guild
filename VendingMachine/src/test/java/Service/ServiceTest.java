/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Dao.Dao;
import Dao.DaoImpl;
import Model.VendableItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dan
 */
public class ServiceTest {

    private Dao dao = new DaoImpl();
    private Service service = new Service(dao);

    private final VendableItem item1 = new VendableItem();
    private final VendableItem item2 = new VendableItem();
    private final VendableItem item3 = new VendableItem();

    //Note: Keys must be larger then the number of items in
    //	    the vending machine, else the file get's overwritten
    //	    incorrectly
    private final int key1 = 3001;
    private final int key2 = 3002;
    private final int key3 = 3003;
    
    //new key that is within the newly mapped list by Service.getItemList() 
    private final int serviceKey1 = service.getItemList().size() + 0;
    private final int serviceKey2 = service.getItemList().size() + 1;
    private final int serviceKey3 = service.getItemList().size() + 2;

    @Before
    public void setUp() {
	item1.setName("itemOne");
	item1.setCost(new BigDecimal("1.00"));
	item1.setQuanity(10);

	item2.setName("itemTwo");
	item2.setCost(new BigDecimal("2.00"));
	item2.setQuanity(20);

	item3.setName("itemThree");
	item3.setCost(new BigDecimal("3.00"));
	item3.setQuanity(30);

	service.addItem(key1, item1);
	service.addItem(key2, item2);
	service.addItem(key3, item3);
    }

    @After
    public void tearDown() {
	service.removeItem(key1);
	service.removeItem(key2);
	service.removeItem(key3);
    }

    @Test
    public void testConvertToBigDecimal() {
	BigDecimal big1 = BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
	BigDecimal bigZ = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	assertEquals(big1, service.convertToBigDecimal(1));
	assertEquals(big1, service.convertToBigDecimal("1"));
	assertEquals(big1, service.convertToBigDecimal("1.0"));
	assertEquals(big1, service.convertToBigDecimal("1.00"));
	assertEquals(bigZ, service.convertToBigDecimal("string"));
    }

    @Test
    public void testUpdateQuanity() throws NoItemInventoryException {
	service.updateQuanity(service.getItem(serviceKey1), -1);
	service.updateQuanity(service.getItem(serviceKey1), -1);

	for (int i = 0; i < 5; i++) {
	    service.updateQuanity(service.getItem(serviceKey2), -1);
		    
	}

	for (int i = 0; i < 10; i++) {
	    service.updateQuanity(service.getItem(serviceKey3), -1);
	}

	assertEquals(8, service.getItem(serviceKey1).getQuanity());
	assertEquals(15, service.getItem(serviceKey2).getQuanity());
	assertEquals(20, service.getItem(serviceKey3).getQuanity());
    }

    @Test
    public void testGetItemList() {
	assertTrue(service.getItemList().size() > 0);
    }

    
    //Not part of the Dao list
    //have new keys due to Service.getItemList()
    @Test
    public void testGetItem() throws NoItemInventoryException {
	int size = service.getItemList().size();
	
	assertTrue(item1.equals(service.getItem(serviceKey1)));
	assertTrue(item2.equals(service.getItem(serviceKey2)));
	assertTrue(item3.equals(service.getItem(serviceKey3)));
    }

    @Test
    public void testCheckFunds() throws Exception {
	//Toonie, loonie, quarter, dime, nickle, penny
	//2.00,	  1.00	  0.25	   0.10	 0.05	 0.01

	//		  2  1  .25  .10  .05  .01
	int[] result1 = new int[]{0, 1, 2, 0, 0, 0};
	int[] result2 = new int[]{0, 0, 0, 1, 1, 3};
	int[] result3 = new int[]{0, 0, 0, 0, 0, 0};

	//						1.00 - starting value
	int[] test1 = service.checkFunds(new BigDecimal("2.50"), serviceKey1);
	//						2.00 - starting value
	int[] test2 = service.checkFunds(new BigDecimal("2.18"), serviceKey2);
	//						3.00 - starting value
	int[] test3 = service.checkFunds(new BigDecimal("3.00"), serviceKey3);

	Assert.assertArrayEquals(result1, test1);
	Assert.assertArrayEquals(result2, test2);
	Assert.assertArrayEquals(result3, test3);

	try {
	    int[] test = service.checkFunds(BigDecimal.ZERO, serviceKey1);
	    fail();
	}
	catch (InsufficientFundsException ex) {
	    //pass
	}

    }

    //===================================================
    public void testAddItem() {
	//pass through method, tested in dao.daoImplTest
    }

    public void testRemoveItem() throws NoItemInventoryException {
	//pass through method, tested in dao.daoImplTest
    }

    @Test
    public void testGetQuanity() throws NoItemInventoryException {
	//pass through method, tested in dao.daoImplTest
    }

}
