package boc.shadow.dao.impl;

import boc.shadow.dao.NotifyDAO;
import boc.shadow.domain.Notify;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by Tong on 2015/8/18.
 */
public class NotifyDaoImpl extends HibernateDaoSupport implements NotifyDAO {
    @Override
    public Notify getNotify(Integer auto_id) {
        return getHibernateTemplate().get(Notify.class, auto_id);
    }

    @Override
    public List<Notify> findAll() {
        return (List<Notify>) getHibernateTemplate().find("from Notify");
    }

    @Override
    public void deleteNotify(Integer auto_id) {
        getHibernateTemplate().delete(getNotify(auto_id));
    }
}
