package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.validate.ImageNewValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @Size(min = 3, max = 20, message = "Invalid Name!!!")
    private String name;
    //@Email(message = "Invalid User Email!!!")
    //by using @Pattern
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid User Email!!!")
    @NotBlank(message = "Email cannot be blank!!")
    private String email;
    @NotBlank(message = "password is required!!!")
    private String password;
    @Size(min = 4, max = 6, message = "Invalid Gender!!!")
    private String gender;
    @NotBlank(message = "write something in about")
    private String about;


    //custom validater
    @ImageNewValid
    private String imageName;

    private Set<RoleDto> roles = new HashSet<>();

}
