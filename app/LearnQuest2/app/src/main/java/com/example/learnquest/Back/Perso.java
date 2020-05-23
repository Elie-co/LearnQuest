package com.example.learnquest.Back;

public class Perso {
    private int atk;
    private int def;
    private int hp;
    private Skin skin;

    public Perso(int atk, int def, int hp, Skin skin) {
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.skin = skin;
    }

    public Perso(){}

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
