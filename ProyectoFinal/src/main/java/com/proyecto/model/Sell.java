package com.proyecto.model;

public class Sell {
    private int idSell;
    private int idEmploy;
    private int idProduct;
    private int quantity;
    private String sellDate;
    private double total;

    // Constructor, getters y setters
    public Sell(int idSell, int idEmploy, int idProduct, int quantity, String sellDate, double total) {
        this.idSell = idSell;
        this.idEmploy = idEmploy;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.sellDate = sellDate;
        this.total = total;
    }

    public int getIdSell() {
        return idSell;
    }

    public int getIdEmploy() {
        return idEmploy;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSellDate() {
        return sellDate;
    }

    public double getTotal() {
        return total;
    }
}