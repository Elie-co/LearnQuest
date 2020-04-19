package com.example.learnquest.Back;

import java.util.HashMap;

public class User {
    private int coin;
    private int experience;
    private Perso perso;
    private double theta;
    private String username;

    public User(int coin, int experience, Perso perso, double theta, String username) {
        this.coin = coin;
        this.experience = experience;
        this.perso = perso;
        this.theta = theta;
        this.username = username;
    }

    public User(){}

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Perso getPerso() {
        return perso;
    }

    public void setPerso(Perso perso) {
        this.perso = perso;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
