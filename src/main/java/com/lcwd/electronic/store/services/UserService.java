package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create

    //User  createUser(User user);
    UserDto createUser(UserDto userDto);


    //update
    UserDto updateUser(UserDto userDto, String userId);


    //delete
    void deleteUser(String userId);


    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sorDir);


    //get single user by id
    UserDto getUserByID(String userId);


    //get single user by email
    UserDto getUserByEmail(String email);


    //search user
    List<UserDto> searchUser(String keyword);

}
