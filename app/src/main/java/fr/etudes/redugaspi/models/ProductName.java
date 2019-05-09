package fr.etudes.redugaspi.models;

import java.io.Serializable;

public class ProductName implements Serializable {
    private String barcode;
    private String name;

    public ProductName(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return this.name;
    }
}
