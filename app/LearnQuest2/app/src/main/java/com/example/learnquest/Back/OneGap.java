package com.example.learnquest.Back;

public class OneGap extends Questions {
    String before;
    String after;
    String answer;

    public OneGap(boolean goodAnswer, double theta, String french, String before, String after, String answer) {
        super(goodAnswer, theta, french);
        this.before = before;
        this.after = after;
        this.answer = answer;
    }

    public OneGap(){}

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
