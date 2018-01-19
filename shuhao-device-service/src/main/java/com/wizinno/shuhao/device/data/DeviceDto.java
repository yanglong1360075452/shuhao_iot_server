package com.wizinno.shuhao.device.data;

import java.util.Date;

public class DeviceDto {
    private Long deviceId;
    private String deviceVer;
    private Integer sensorCount;
    private Integer driverCount;
    private String ioCfg;
    private Integer samplingPeriod;
    private Date ioCfgTime;
    private Integer isOnline;
    private String isOnlineDesc; //设备在线状态
    private  Date onlineTime;
    private  String sinarioCfg;
    private Date sinarioCfgTime;
    private String userPhone;
    private Long userId;

    public String getIsOnlineDesc() {
        return isOnlineDesc;
    }

    public void setIsOnlineDesc(String isOnlineDesc) {
        this.isOnlineDesc = isOnlineDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceVer() {
        return deviceVer;
    }

    public void setDeviceVer(String deviceVer) {
        this.deviceVer = deviceVer;
    }

    public Integer getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(Integer sensorCount) {
        this.sensorCount = sensorCount;
    }

    public Integer getDriverCount() {
        return driverCount;
    }

    public void setDriverCount(Integer driverCount) {
        this.driverCount = driverCount;
    }

    public String getIoCfg() {
        return ioCfg;
    }

    public void setIoCfg(String ioCfg) {
        this.ioCfg = ioCfg;
    }

    public Integer getSamplingPeriod() {
        return samplingPeriod;
    }

    public void setSamplingPeriod(Integer samplingPeriod) {
        this.samplingPeriod = samplingPeriod;
    }

    public Date getIoCfgTime() {
        return ioCfgTime;
    }

    public void setIoCfgTime(Date ioCfgTime) {
        this.ioCfgTime = ioCfgTime;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getSinarioCfg() {
        return sinarioCfg;
    }

    public void setSinarioCfg(String sinarioCfg) {
        this.sinarioCfg = sinarioCfg;
    }

    public Date getSinarioCfgTime() {
        return sinarioCfgTime;
    }

    public void setSinarioCfgTime(Date sinarioCfgTime) {
        this.sinarioCfgTime = sinarioCfgTime;
    }
}
