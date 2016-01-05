package boc.shadow.domain;

import java.util.Date;

/**
 * Created by Tong on 2015/8/18.
 */
public class Notify {
    private Integer auto_id;
    private Item item;
    private Date auto_date;
    private String auto_type;
    private User user;

    public Notify() {
    }

    public Integer getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(Integer auto_id) {
        this.auto_id = auto_id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAuto_date() {
        return auto_date;
    }

    public void setAuto_date(Date auto_date) {
        this.auto_date = auto_date;
    }

    public String getAuto_type() {
        return auto_type;
    }

    public void setAuto_type(String auto_type) {
        this.auto_type = auto_type;
    }


}
