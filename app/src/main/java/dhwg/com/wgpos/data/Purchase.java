package dhwg.com.wgpos.data;

import java.util.Date;

/**
 * A purchase of a product made by an inhabitant.
 */

public class Purchase {

    private int id;
    private Date date;
    private int buyer;
    private int product;

    public Purchase(int buyer, int product) {
        this.buyer = buyer;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

}
