package com.example.manager.model;

/**
 * 로그인에 사용되는 정보
 */
public class LoginInfo {
    private String id;
    private String password;

    public LoginInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
