package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
