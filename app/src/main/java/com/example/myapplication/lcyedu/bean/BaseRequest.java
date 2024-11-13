package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

public class BaseRequest {
    @SerializedName("appver")
    private String appver;
    @SerializedName("did")
    private String did;

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
