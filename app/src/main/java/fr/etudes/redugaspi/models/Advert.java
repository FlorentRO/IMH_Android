package fr.etudes.redugaspi.models;

public class Advert {
    private Product product;
    private String price;

    public Advert(Product product, String price) {

        this.product = product;
        this.price = price;
    }

    public Product getProduct() { return product; }
    public String getPrice() { return price; }
}
