package fr.etudes.redugaspi.models;

public class User {
    private String name;
    private String num;

    public User(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getPseudo() {
        return name;
    }
    public void setPseudo(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
}