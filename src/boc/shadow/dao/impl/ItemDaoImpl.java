package boc.shadow.dao.impl;

import boc.shadow.dao.ItemDAO;
import boc.shadow.domain.Item;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Created by Tong on 2015/8/18.
 */
public class ItemDaoImpl extends HibernateDaoSupport implements ItemDAO {
    @Override
    public Item getItem(Integer item_id) {
        return getHibernateTemplate().get(Item.class, item_id);
    }
}
