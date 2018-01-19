package com.wizinno.shuhao.user.data;

import java.util.Date;

public class AppVerDto {
    private Long id;
    private int platform;
    private Integer appVer;
    private String describ;
    private Date relTime;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }


    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAppVer() {
        return appVer;
    }

    public void setAppVer(Integer appVer) {
        this.appVer = appVer;
    }

    public Date getRelTime() {
        return relTime;
    }

    public void setRelTime(Date relTime) {
        this.relTime = relTime;
    }
}
