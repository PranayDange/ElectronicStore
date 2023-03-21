package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    //ek interface dusre interface ko extend karta hai
    //ek class dusre class ko implements karti hai

    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

}
