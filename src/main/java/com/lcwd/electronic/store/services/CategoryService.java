package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);
    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //getall
    //we will be needing pagination here also so  we are using PagableResponse
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);

    //get single category detail
    CategoryDto get(String categoryId);

    //search
}
