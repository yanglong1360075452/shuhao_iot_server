package com.wizinno.shuhao.condition;

/**
 * Created by HP on 2017/11/8.
 */
public class DeviceCondition extends PageCondition {

    private String phone;

    private Long deviceId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
