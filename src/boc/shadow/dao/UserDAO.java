package boc.shadow.dao;

import boc.shadow.domain.User;

/**
 * Created by Tong on 2015/8/18.
 */

public interface UserDAO {

    // get the user information
    User getUser (Integer user_id);

}
