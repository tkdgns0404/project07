package com.example.manager.model;

/**
 * 회원 정보 모델
 */

public class Board {
    private int idx;
    private String title;
    private String writer;
    private String content;
    private int cnt;

    private boolean isChecked;

    public Board(int idx, String title, String writer, String content, int cnt) {
        this.idx = idx;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.cnt = cnt;
    }


    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
