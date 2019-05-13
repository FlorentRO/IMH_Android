package fr.etudes.redugaspi.models;

import java.io.Serializable;

public class Advert implements Serializable {
    private Product product;
    private String price;
    private String shop;
    private double latitude;
    private double longitude;
    private String descAdd;

    public Advert(Product product, String price, String shop, double latitude, double longitude, String descAdd) {
        this.shop = shop;
        this.product = product;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.descAdd = descAdd;
    }

    public String getShop() {
        return shop;
    }
    public Product getProduct() { return product; }
    public String getPrice() { return price; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return  longitude; }
    private String getDescAdd() { return descAdd; }
}
