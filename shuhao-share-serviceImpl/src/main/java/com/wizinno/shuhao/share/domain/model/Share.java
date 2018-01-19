package com.wizinno.shuhao.share.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Share implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share.user_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share.share_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    private Long shareId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share.device_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    private Long deviceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share.share_time
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    private Date shareTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table share
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share.user_id
     *
     * @return the value of share.user_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share.user_id
     *
     * @param userId the value for share.user_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share.share_id
     *
     * @return the value of share.share_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public Long getShareId() {
        return shareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share.share_id
     *
     * @param shareId the value for share.share_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share.device_id
     *
     * @return the value of share.device_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share.device_id
     *
     * @param deviceId the value for share.device_id
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share.share_time
     *
     * @return the value of share.share_time
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share.share_time
     *
     * @param shareTime the value for share.share_time
     *
     * @mbggenerated Wed Sep 27 15:39:44 CST 2017
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }
}