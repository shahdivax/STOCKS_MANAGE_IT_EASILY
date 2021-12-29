package com.divax.stocks;

public class Stocks {
    String Name,price,quantity,net;


    public Stocks(String name, String price, String net , String quantity) {
        Name = name;
        this.price = price;
        this.quantity = quantity;
        this.net = net;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
