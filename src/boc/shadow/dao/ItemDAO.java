package boc.shadow.dao;

import boc.shadow.domain.Item;

/**
 * Created by Tong on 2015/8/18.
 */
public interface ItemDAO {
    // get the item information
    Item getItem (Integer item_id);
}
