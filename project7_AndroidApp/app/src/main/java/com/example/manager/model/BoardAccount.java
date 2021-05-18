package com.example.manager.model;

/**
 * 회원 정보 모델
 */

public class BoardAccount {
    private static BoardAccount instance;

    public static BoardAccount getInstance() {
        if (instance == null) {
            instance = new BoardAccount();
        }
        return instance;
    }

    private Board board;

    private BoardAccount() { }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
