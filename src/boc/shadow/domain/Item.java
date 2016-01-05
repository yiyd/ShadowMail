package boc.shadow.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tong on 2015/8/18.
 */
public class Item implements Serializable {
    private Integer item_id;
    private String item_name;
    private User userCreator;
    private User userFollower;
    private Date item_create_time;
    private String item_description;
    private Integer item_type_id;
    private String item_state;
    private Integer item_priority_id;

    public Item() {
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public User getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(User userFollower) {
        this.userFollower = userFollower;
    }

    public Date getItem_create_time() {
        return item_create_time;
    }

    public void setItem_create_time(Date item_create_time) {
        this.item_create_time = item_create_time;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public Integer getItem_type_id() {
        return item_type_id;
    }

    public void setItem_type_id(Integer item_type_id) {
        this.item_type_id = item_type_id;
    }

    public String getItem_state() {
        return item_state;
    }

    public void setItem_state(String item_state) {
        this.item_state = item_state;
    }

    public Integer getItem_priority_id() {
        return item_priority_id;
    }

    public void setItem_priority_id(Integer item_priority_id) {
        this.item_priority_id = item_priority_id;
    }
}
