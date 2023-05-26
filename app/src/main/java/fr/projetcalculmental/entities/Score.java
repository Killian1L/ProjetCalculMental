package fr.projetcalculmental.entities;

public class Score extends BaseEntity{
    private String pseudo;
    private Integer score;
    private int difficulty;

    public Score(String pseudo, Integer score, int difficulty) {
        this.pseudo = pseudo;
        this.score = score;
        this.difficulty = difficulty;
    }

    public Score() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}