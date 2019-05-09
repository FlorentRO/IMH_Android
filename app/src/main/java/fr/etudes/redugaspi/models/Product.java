package fr.etudes.redugaspi.models;

import android.app.AlarmManager;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

public class Product implements Serializable {

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

    public long getDateMillis() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(String.format("%s/%s/%s", day, month, year));
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}