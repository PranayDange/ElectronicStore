package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductDto {


    private String productId;
    private String title;

    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

    private String productImageName;
    private CategoryDto category;
}
