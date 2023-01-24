package com.repiso.myquizapp;

public class Score {

    private String username, categoryname;
    private int score;

    public Score() {
    }

    public Score(String username, String categoryname, int score) {
        this.username = username;
        this.categoryname = categoryname;
        this.score = score;
    }

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
