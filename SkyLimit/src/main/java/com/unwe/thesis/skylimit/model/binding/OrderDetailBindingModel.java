package com.unwe.thesis.skylimit.model.binding;

import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import com.unwe.thesis.skylimit.model.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetailBindingModel {
    //TODO: finish this
    private BigDecimal amount;
    private LocalDateTime orderDate;
    private String zip;
    private UserEntity buyer; //TODO: try with user binding model and validations
    private ProductEntity product;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
