package fr.projetcalculmental.entities;

public class Score extends BaseEntity{
    private String pseudo;
    private Integer score;

    public Score(String pseudo, Integer score) {
        this.pseudo = pseudo;
        this.score = score;
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
}