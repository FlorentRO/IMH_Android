package fr.etudes.redugaspi.models;

public class User {
    private String name;
    private Number num;

    public User(String name, Number num) {
        this.name = name;
        this.num = num;
    }

    public String getPseudo() {
        return name;
    }
    public void setPseudo(String name) {
        this.name = name;
    }

    public Number getNum() {
        return num;
    }
    public void setNum(Number num) {
        this.num = num;
    }
}