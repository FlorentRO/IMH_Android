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

    public static int compareDates(Product p1, Product p2) {
        long diff = 365 * AlarmManager.INTERVAL_DAY * (p1.getYear() - p2.getYear()) +
                30 * AlarmManager.INTERVAL_DAY * (p1.getMonth() - p2.getMonth()) +
                AlarmManager.INTERVAL_DAY * (p1.getDay() - p2.getDay());
        return diff>0 ? 1 : diff<0 ? -1 : 0;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBarcode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Product) {
                return getDate().equals(((Product) obj).getDate()) && getBarCode().equals(((Product) obj).getBarCode());
            }
        }
        return false;
    }
}