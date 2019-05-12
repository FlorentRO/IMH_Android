package fr.etudes.redugaspi.models;


import java.io.Serializable;

public class ProductCourses implements Serializable {

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

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ProductCourses) {
                return productName.equals(((ProductCourses) obj).productName)&&quantity==((ProductCourses) obj).quantity;
            }
        }
        return false;
    }
}
