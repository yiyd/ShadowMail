package boc.shadow.dao;

import boc.shadow.domain.Notify;

import java.util.List;

/**
 * Created by Tong on 2015/8/18.
 */
public interface NotifyDAO {
    // get method
    Notify getNotify (Integer auto_id);

    // delete method by id
    void deleteNotify (Integer auto_id);

    // findAll
    List<Notify> findAll ();
}
