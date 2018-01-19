package com.wizinno.shuhao.user.data;

public class AuthoCodeDto {
    private Long id;
    private String phone;
    private String authoCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthoCode() {
        return authoCode;
    }

    public void setAuthoCode(String authoCode) {
        this.authoCode = authoCode;
    }
}
