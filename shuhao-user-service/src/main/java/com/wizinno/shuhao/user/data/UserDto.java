package com.wizinno.shuhao.user.data;

import java.util.Date;
import java.util.List;

public class UserDto {
    private Long userId;

    private String phone;

    private String password;

    private String userName;

    private Date registerTime;

    private Integer isActived;

    private List<Long> userShares;

    private List<Long> friendShares;

    public List<Long> getUserShares() {
        return userShares;
    }

    public void setUserShares(List<Long> userShares) {
        this.userShares = userShares;
    }

    public List<Long> getFriendShares() {
        return friendShares;
    }

    public void setFriendShares(List<Long> friendShares) {
        this.friendShares = friendShares;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getIsActived() {
        return isActived;
    }

    public void setIsActived(Integer isActived) {
        this.isActived = isActived;
    }
}

