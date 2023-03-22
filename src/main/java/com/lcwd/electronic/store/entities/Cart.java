package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    private String cartId;

    private Date createdAt;

    @OneToOne
    private User user;

    //mapping cart-item
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();
    //use sets if you do not want to have duplicates
    //or you remove can use fetchtype.eager
}
