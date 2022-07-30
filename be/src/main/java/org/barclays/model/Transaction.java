package org.barclays.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    public enum Type{
        BUY("BUY"),
        CANCEL("CANCEL");

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
    private Transaction.Type transactionType;

    @OneToOne
    private InventoryAction inventoryActionId;

    @OneToOne
    private User userId;
    private Date created_at = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Type transactionType) {
        this.transactionType = transactionType;
    }

    public InventoryAction getInventoryActionId() {
        return inventoryActionId;
    }

    public void setInventoryActionId(InventoryAction inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
