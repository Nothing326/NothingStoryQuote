package nothing.impossible.com.nothing.Model;

/**
 * Created by User on 7/31/18.
 */
public class MindSetQuiz {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String questions;
    int strAgree,agree,mostAgree;
    int mostDisgree,disagree,strDisagree;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getStrAgree() {
        return strAgree;
    }

    public void setStrAgree(int strAgree) {
        this.strAgree = strAgree;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getMostAgree() {
        return mostAgree;
    }

    public void setMostAgree(int mostAgree) {
        this.mostAgree = mostAgree;
    }

    public int getMostDisgree() {
        return mostDisgree;
    }

    public void setMostDisgree(int mostDisgree) {
        this.mostDisgree = mostDisgree;
    }

    public int getDisagree() {
        return disagree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public int getStrDisagree() {
        return strDisagree;
    }

    public void setStrDisagree(int strDisagree) {
        this.strDisagree = strDisagree;
    }
}
