package com.example.order.domain;

import java.util.List;
import java.util.Set;

public class OrderDTO {
    private long id;
    private long userId;
    private Set<Long> productIds;

    public OrderDTO() {
    }

    public OrderDTO(long id, long userId, Set<Long> productIds) {
        this.id = id;
        this.userId = userId;
        this.productIds = productIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }
}
