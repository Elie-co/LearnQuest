package com.example.learnquest.Back;

public class Skin {
    private int price;
    private String base1;
    private String base2;

    public Skin(int price, String base1, String base2) {
        this.price = price;
        this.base1 = base1;
        this.base2 = base2;
    }

    public Skin(){}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBase1() {
        return base1;
    }

    public void setBase1(String base1) {
        this.base1 = base1;
    }

    public String getBase2() {
        return base2;
    }

    public void setBase2(String base2) {
        this.base2 = base2;
    }
}
