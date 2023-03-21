package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "category_title", length = 60, nullable = false)
    private String title;
    @Column(name = "category_desc", length = 100)
    private String description;
    private String coverImage;

    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    //cascade=CascadeType.ALL ....> cascade is used so that if category is removed then product is also removed or updated
    //FetchType.LAZY it means jb hmm category fetch karenge uss samay products fetch na ho....products fetch honge on demand pe
    //mappedBy = "category" is used so that both tables should not contain same column
}
