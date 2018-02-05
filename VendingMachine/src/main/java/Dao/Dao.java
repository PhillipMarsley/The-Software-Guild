/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.VendableItem;
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
     * Adds a VendableItem to a collection.
     *
     * @param itemKey The Map itemKey of the Collection where VendableItems are stored.
     * @param item Takes a VendableItem object.
     * @return The VendableItem object that was added to a collection.
     * <p>
     * If the VendableItem added replaces an existing item in a collection, the
     * return is the replaced VendableItem
     */
    public VendableItem addItem(int itemKey, VendableItem item);

    /**
     * Removes a VendableItem to a collection.
     *
     * @param itemKey The Map itemKey of the Collection where VendableItems are stored.
     * @return The VendableItem object that was removed from a collection.
     */
    public VendableItem removeItem(int itemKey);

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
