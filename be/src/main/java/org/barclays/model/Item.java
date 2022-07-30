package org.barclays.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double mrp;
    private int disocuntPercentage;
    private Long availableQuantity;
    private int discountedSellingPrice;
    private Double weightInGms;
    private boolean outOfStock;
    private Long quantity;
    private Date created_at = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public int getDisocuntPercentage() {
        return disocuntPercentage;
    }

    public void setDisocuntPercentage(int disocuntPercentage) {
        this.disocuntPercentage = disocuntPercentage;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getDiscountedSellingPrice() {
        return discountedSellingPrice;
    }

    public void setDiscountedSellingPrice(int discountedSellingPrice) {
        this.discountedSellingPrice = discountedSellingPrice;
    }

    public Double getWeightInGms() {
        return weightInGms;
    }

    public void setWeightInGms(Double weightInGms) {
        this.weightInGms = weightInGms;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
