package com.example.order.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Long userId;

    @Column
    @Convert(converter = ProductIdsConverter.class)
    private Set<Long> productIds;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }
}
