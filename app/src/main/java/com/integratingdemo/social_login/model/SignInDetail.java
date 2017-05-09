package com.integratingdemo.social_login.model;

/**
 * Created by Janki on 30-01-2017.
 */

public class SignInDetail {
    private String muserName, mEmail, mprofilePic;
    private Integer mloginType;

    public SignInDetail(String muserName, String mEmail, String mprofilePic) {
        this.muserName = muserName;
        this.mEmail = mEmail;
        this.mprofilePic = mprofilePic;
    }

    public SignInDetail(String muserName, String mEmail, String mprofilePic, Integer mloginType) {
        this.muserName = muserName;
        this.mEmail = mEmail;
        this.mprofilePic = mprofilePic;
        this.mloginType = mloginType;
    }

    public SignInDetail() {
    }

    public Integer getMloginType() {
        return mloginType;
    }

    public void setMloginType(Integer mloginType) {
        this.mloginType = mloginType;
    }

    public String getMuserName() {
        return muserName;
    }

    public void setMuserName(String muserName) {
        this.muserName = muserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getMprofilePic() {
        return mprofilePic;
    }

    public void setMprofilePic(String mprofilePic) {
        this.mprofilePic = mprofilePic;
    }
}