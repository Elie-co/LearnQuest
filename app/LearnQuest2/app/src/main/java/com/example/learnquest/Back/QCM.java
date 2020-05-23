package com.example.learnquest.Back;

public class QCM extends Questions {

    String before;
    String after;
    String answer;
    String[] distractors;

    public QCM(boolean goodAnswer, double theta, String french, String before, String after, String answer, String[] distractors) {
        super(goodAnswer, theta, french);
        this.before = before;
        this.after = after;
        this.answer = answer;
        this.distractors = distractors;
    }

    public QCM() {}

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

    public String[] getDistractors() {
        return distractors;
    }

    public void setDistractors(String[] distractors) {
        this.distractors = distractors;
    }
}
