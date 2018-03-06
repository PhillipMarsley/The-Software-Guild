/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.VendableItem;
import java.util.List;

/**
 *
 * @author Dan
 */
public interface Dao {

    /**
     * Gets the value of the quanity field of a VendableItem
     *
     * @param item takes a VendableItem
     * @return the item's quanity field
     */
    public int getQuanity(VendableItem item);

    /**
     * Update the Quanity field of a VendableItem
     *
     * @param item Takes a VendableItem object.
     * @param count The amount to add to item's quanity field.
     * <p>
     * If count is a positive value, item's quanity field is incremented.
     * <p>
     * If count is a negative value, item's quanity field is decremented.
     */
    public void updateQuanity(VendableItem item, int count);

    /**
     *
     * @param itemKey The Map itemKey of the Collection where VendableItems are stored.
     * @return A VendableItem from a Map
     */
    public VendableItem getItem(int itemKey);

    /**
     *
     * @return A List of VendableItems.
     */
    public List<VendableItem> getItemList();
}
