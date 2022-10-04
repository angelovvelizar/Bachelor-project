package com.unwe.thesis.skylimit.model.binding;

import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import com.unwe.thesis.skylimit.model.entity.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetailBindingModel {
    private BigDecimal amount;
    private LocalDateTime orderDate;
    @NotBlank
    private String zip;
    private String buyer;
    private Long productId;

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

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
