package com.rauniyargames.moviequiz.helperclasses;

/**
 * Created by lenovo on 3/28/2018.
 */

public class Questions {
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    String question;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    String hint;

    public String getEmojiNAme() {
        return emojiNAme;
    }

    public void setEmojiNAme(String emojiNAme) {
        this.emojiNAme = emojiNAme;
    }

    String emojiNAme;
}
