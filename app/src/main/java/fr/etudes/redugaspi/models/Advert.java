package fr.etudes.redugaspi.models;

import java.io.Serializable;

public class Advert implements Serializable {
    private Product product;
    private String price;

    public Advert(Product product, String price) {

        this.product = product;
        this.price = price;
    }

    public Product getProduct() { return product; }
    public String getPrice() { return price; }
}
