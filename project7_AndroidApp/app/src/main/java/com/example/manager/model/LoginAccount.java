package com.example.manager.model;

/**
 * 로그인 된 계정
 */
public class LoginAccount {
    private static LoginAccount instance;

    public static LoginAccount getInstance() {
        if (instance == null) {
            instance = new LoginAccount();
        }
        return instance;
    }

    private Member member;

    private LoginAccount() { }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
