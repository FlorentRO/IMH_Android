package fr.etudes.redugaspi.models;

public class Product {

    private String barCode;
    private int day;
    private int month;
    private int year;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public Product(String barCode, int quantity, int day, int month, int year) {
        this.barCode = barCode;
        this.day = day;
        this.month = month;
        this.year = year;
        this.quantity = quantity;
    }

    public String getBarCode() {
        return barCode;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDate() {
        return day + " / " + month + " / " + year;
    }

}