package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

/*{
  "code": 200,
  "msg": "",
  "data": {
    "ts": "1647853086453",
    "sign": "02c784d5f2f8c08d41a2cd23c8e58f83",
    "token": "25c08093-0eb1-4454-beca-b3f03b55737e-12022022413676887",
    "code": "xPtiaZpHg36%2BXQoacJmBKn3e4pzw836K0CwZI2IKyOgVDOUNABDS6mvKXmsRlPAYr8akEdVaUzASuI7xtIlX6Q=="
  }
}*/
public class CourseSignResult {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("ts")
        private String ts;
        @SerializedName("sign")
        private String sign;
        @SerializedName("token")
        private String token;
        @SerializedName("code")
        private String code;

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
