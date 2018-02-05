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
import java.util.Map;
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
    private VendableItem item1 = new VendableItem();
    private VendableItem item2 = new VendableItem();
    private VendableItem item3 = new VendableItem();

    public ServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
	item1.setName("itemOne");
	item1.setCost(new BigDecimal("1.50"));
	item1.setQuanity(10);

	item2.setName("itemTwo");
	item2.setCost(new BigDecimal("2.50"));
	item2.setQuanity(20);

	item3.setName("itemThree");
	item3.setCost(new BigDecimal("3.50"));
	item3.setQuanity(30);

	service.addItem(0, item1);
	service.addItem(1, item2);
	service.addItem(2, item3);
    }

    @After
    public void tearDown() {

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
    public void testCheckFunds() throws Exception {
	//Toonie, loonie, quarter, dime, nickle, penny
	//2.00,	  1.00	  0.25	   0.10	 0.05	 0.01

	int[] result1 = new int[]{0, 1, 0, 0, 0, 0};
	int[] result2 = new int[]{0, 0, 0, 1, 1, 3};
	int[] result3 = new int[]{0, 0, 0, 0, 0, 0};

	int[] test1 = service.checkFunds(new BigDecimal("2.50"), 0);
	int[] test2 = service.checkFunds(new BigDecimal("2.68"), 1);
	int[] test3 = service.checkFunds(new BigDecimal("3.50"), 2);

	Assert.assertArrayEquals(result1, test1);
	Assert.assertArrayEquals(result2, test2);
	Assert.assertArrayEquals(result3, test3);

	try {
	    int[] test = service.checkFunds(BigDecimal.ZERO, 0);
	    fail();
	}
	catch(InsufficientFundsException ex) {
	    //pass
	}

    }

    @Test
    public void testGetQuanity() {
	assertEquals(10, service.getQuanity(service.getItem(0)));
	assertEquals(20, service.getQuanity(service.getItem(1)));
	assertEquals(30, service.getQuanity(service.getItem(2)));
    }

    @Test
    public void testUpdateQuanity() {
	service.updateQuanity(service.getItem(0), -1);
	service.updateQuanity(service.getItem(0), -1);

	for (int i = 0; i < 5; i++) {
	    service.updateQuanity(service.getItem(1), -1);
	}

	for (int i = 0; i < 10; i++) {
	    service.updateQuanity(service.getItem(2), -1);
	}

	assertEquals(8, service.getItem(0).getQuanity());
	assertEquals(15, service.getItem(1).getQuanity());
	assertEquals(20, service.getItem(2).getQuanity());
    }

    @Test
    public void testAddItem() {

    }

    @Test
    public void testRemoveItem() {

    }

    @Test
    public void testGetItem() {
	assertTrue(item1.equals(service.getItem(0)));
	assertTrue(item2.equals(service.getItem(1)));
	assertTrue(item3.equals(service.getItem(2)));
    }

    @Test
    public void testGetItemList() {
	Map<Integer, VendableItem> items = service.getItemList();
	assertTrue(service.getItemList().size() > 0);
    }

}
