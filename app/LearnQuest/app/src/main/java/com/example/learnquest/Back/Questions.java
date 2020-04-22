package com.example.learnquest.Back;

public class Questions {
    boolean goodAnswer;
    double theta;
    String french;

    public Questions(boolean goodAnswer, double theta, String french) {
        this.goodAnswer = goodAnswer;
        this.theta = theta;
        this.french = french;
    }

    public Questions(){}

    public boolean isGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(boolean goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }
}
