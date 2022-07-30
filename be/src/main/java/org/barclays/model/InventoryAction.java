package org.barclays.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory_action")
public class InventoryAction {

    public enum Type{
        ADD("ADD"),
        REMOVE("REMOVE");

        public String getType() {
            return type;
        }

        private final String type;

        Type(String type) {
            this.type = type;
        }
    }

    @Id
    @GeneratedValue
    private Long id;
    private InventoryAction.Type inventoryActionType;

    @OneToOne
    private Item itemId;

    @OneToOne
    private Shop shopId;
    private Date created_at = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getInventoryActionType() {
        return inventoryActionType;
    }

    public void setInventoryActionType(Type inventoryActionType) {
        this.inventoryActionType = inventoryActionType;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
