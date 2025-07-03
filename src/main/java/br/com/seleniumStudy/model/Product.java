package br.com.seleniumStudy.model;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final BigDecimal price;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product(String name, String price) {
        this.name = name;
        this.price = new BigDecimal(price.replace("R$", "").replace(",", "."));
    }
}
