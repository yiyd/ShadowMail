package boc.shadow.dao.impl;

import boc.shadow.dao.UserDAO;
import boc.shadow.domain.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Created by Tong on 2015/8/18.
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDAO {
    @Override
    public User getUser(Integer user_id) {
        if (getHibernateTemplate().get(User.class, user_id) == null) {
            System.out.println("it` null");
        }
        return getHibernateTemplate().get(User.class, user_id);
    }
}
