/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.VendableItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dan
 */
public class DaoImplTest {

    private Dao dao = new DaoImpl();

    private VendableItem item1 = new VendableItem();
    private VendableItem item2 = new VendableItem();
    private VendableItem item3 = new VendableItem();

    //Note: Keys must be larger then the number of items in
    //	    the vending machine, else the file get's overwritten
    //	    incorrectly
    private int key1 = 3001;
    private int key2 = 3002;
    private int key3 = 3003;

    public DaoImplTest() {
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

	dao.addItem(key1, item1);
	dao.addItem(key2, item2);
	dao.addItem(key3, item3);
    }

    @After
    public void tearDown() {
	dao.removeItem(key1);
	dao.removeItem(key2);
	dao.removeItem(key3);
    }

    @Test
    public void testCheckQuanity() {
	assertEquals(10, dao.getQuanity(dao.getItem(key1)));
	assertEquals(20, dao.getQuanity(dao.getItem(key2)));
	assertEquals(30, dao.getQuanity(dao.getItem(key3)));
    }

    @Test
    public void testUpdateQuanity() {
	dao.updateQuanity(dao.getItem(key1), -1);
	dao.updateQuanity(dao.getItem(key1), -1);

	for (int i = 0; i < 5; i++) {
	    dao.updateQuanity(dao.getItem(key2), -1);
	}

	for (int i = 0; i < 10; i++) {
	    dao.updateQuanity(dao.getItem(key3), -1);
	}

	assertEquals(8, dao.getItem(key1).getQuanity());
	assertEquals(15, dao.getItem(key2).getQuanity());
	assertEquals(20, dao.getItem(key3).getQuanity());
    }

    @Test
    public void testAddItem() {
	VendableItem item4 = new VendableItem();
	item3.setName("itemThree");
	item3.setCost(new BigDecimal("3.50"));
	item3.setQuanity(30);

	dao.addItem(4000, item4);

	assertTrue(dao.getItemList().contains(dao.getItem(4000)));
    }

    @Test
    public void testRemoveItem() {
	dao.removeItem(4000);

	assertFalse(dao.getItemList().contains(dao.getItem(4000)));
    }

    @Test
    public void testGetItem() {
	assertTrue(item1.equals(dao.getItem(key1)));
	assertTrue(item2.equals(dao.getItem(key2)));
	assertTrue(item3.equals(dao.getItem(key3)));
    }

    @Test
    public void testGetItemAll() {
	List<VendableItem> items = dao.getItemList();
	assertTrue(dao.getItemList().size() > 0);
    }

}
