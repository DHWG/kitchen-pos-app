package dhwg.com.wgpos.data;

import com.google.gson.annotations.SerializedName;

/**
 * A product that can be bought.
 */

public class Product {

    private int id;
    private String name;
    @SerializedName("unit_price")
    private double unitPrice;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format("[Product: %s]", this.getName());
    }
}
