package dhwg.com.wgpos.data;

/**
 * An inhabitant of the flat share.
 */

public class Inhabitant {

    private int id;
    private String username;
    private double balance;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("[User: %s]", this.getUsername());
    }
}
