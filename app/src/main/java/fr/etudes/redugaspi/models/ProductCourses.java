package fr.etudes.redugaspi.models;


public class ProductCourses {

    private String productName;
    private int quantity;


    public ProductCourses(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) { this.productName= productName; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
