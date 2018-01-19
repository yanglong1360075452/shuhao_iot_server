package com.wizinno.shuhao.condition;

/**
 * Created by HP on 2017/11/6.
 */
public class UserCondition extends PageCondition{

    private  String phone;

    private Long userId;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
