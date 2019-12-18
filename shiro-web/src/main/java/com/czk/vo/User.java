package com.czk.vo;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/16 16:20
 */
public class User {
    private String userName;

    private String password;

    private boolean remenberMe;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemenberMe() {
        return remenberMe;
    }

    public void setRemenberMe(boolean remenberMe) {
        this.remenberMe = remenberMe;
    }
}
