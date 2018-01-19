package com.wizinno.shuhao.condition;

import java.util.Date;

/**
 * Created by HP on 2017/11/7.
 */
public class DataCondition extends PageCondition {

    private Long deviceId;

    private Date startTime;

    private Date endTime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
