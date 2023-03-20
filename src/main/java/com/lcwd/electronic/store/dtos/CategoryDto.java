package com.lcwd.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private String categoryId;

    @NotBlank(message = "title is required")
   // @Min(value = 4, message = "title must be of minimum 4 characters !! ")
    @Size(min = 4)
    private String title;
    @NotBlank(message = "description required !!")
    private String description;

    private String coverImage;
}
