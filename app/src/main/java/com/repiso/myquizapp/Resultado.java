package com.repiso.myquizapp;

import java.time.LocalDate;

public class Resultado {

    private int ID, userID, categoryID, aciertos, fallos, enBlanco, score;
    private String username;

    public Resultado() {
    }

    /**
     * Resultados de la partida
     * @param userID
     * @param categoryID
     * @param aciertos
     * @param fallos
     * @param enBlanco
     * @param score
     */
    public Resultado(int userID, int categoryID, int aciertos, int fallos, int enBlanco, int score) {
        this.userID = userID;
        this.categoryID = categoryID;
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.enBlanco = enBlanco;
        this.score = score;
    }

    public Resultado(int userID, int categoryID, int aciertos, int fallos, int enBlanco, int score, String username) {
        this.userID = userID;
        this.categoryID = categoryID;
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.enBlanco = enBlanco;
        this.score = score;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    public void setFallos(int fallos) {
        this.fallos = fallos;
    }

    public int getEnBlanco() {
        return enBlanco;
    }

    public void setEnBlanco(int enBlanco) {
        this.enBlanco = enBlanco;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
