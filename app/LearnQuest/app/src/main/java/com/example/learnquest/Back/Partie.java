package com.example.learnquest.Back;

import java.util.ArrayList;
import java.util.HashSet;

public class Partie {
    private Perso user;
    private Perso adversaire;
    private ArrayList<Questions> questions;
    private boolean win;

    public Partie(Perso user, Perso adversaire, ArrayList<Questions> questions) {
        this.user = user;
        this.adversaire = adversaire;
        this.questions = questions;
    }

    public Partie(){}

    public Perso getUser() {
        return user;
    }

    public void setUser(Perso user) {
        this.user = user;
    }

    public Perso getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(Perso adversaire) {
        this.adversaire = adversaire;
    }

    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void addQuestion(Questions q) {this.questions.add(q);}

    public boolean isEnd() {
        if(user.getHp() <= 0){
            this.win = false;
            return true;
        } else if(adversaire.getHp() <= 0){
            this.win = true;
            return true;
        } else {return false;}
    }

    
}
