package fr.etudes.redugaspi.models;

import java.io.Serializable;

public class Advert implements Serializable {
    private Product product;
    private String price;
    private String shop;

    public Advert(Product product, String price, String shop) {
        this.shop = shop;
        this.product = product;
        this.price = price;
    }

    public String getShop() {
        return shop;
    }
    public Product getProduct() { return product; }
    public String getPrice() { return price; }
}
