package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public UserDto createUser(UserDto userDto) {
        //genrate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto -->entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        //entity -->dto
        UserDto newDto = entityToDao(savedUser);
        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id"));
        user.setName(userDto.getName());
        //email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDao(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id"));
        //delete user
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);

        //this is now used in helper package    ---    Helper.getPageableResponse()
        /*List<User> users = page.getContent();
        List<UserDto> dtoList = users.stream().map(user -> entityToDao(user)).collect(Collectors.toList());
        PageableResponse<UserDto> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());*/

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserByID(String userId) {
        // User userR = userRepository.findById(userId).get();//this will throw element not found exception in case ID  is not found
        //to throw custom exception we are using orElseThrow() method
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id"));

        return entityToDao(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found By Email"));
        return entityToDao(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDao(user)).collect(Collectors.toList());

        return dtoList;
    }


    //these methods are for conversion purpose only
    //In thses methods one object is mapped to another object manually
    //these can be done using model mapper library

    //without mapper
 /*   private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();
        return user;
    }*/
    //with mapper

    private User dtoToEntity(UserDto userDto) {
       /* User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();*/
        return mapper.map(userDto, User.class);
    }


    //without mapper

    /* private UserDto entityToDao(User savedUser) {
         UserDto userDto = UserDto.builder()
                 .userId(savedUser.getUserId())
                 .name(savedUser.getName())
                 .email(savedUser.getEmail())
                 .password(savedUser.getPassword())
                 .about(savedUser.getAbout())
                 .gender(savedUser.getGender())
                 .imageName(savedUser.getImageName())
                 .build();
         return userDto;
     }*/
    //with mapper
    private UserDto entityToDao(User savedUser) {
        /*UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imageName(savedUser.getImageName())
                .build();*/
        return mapper.map(savedUser, UserDto.class);
    }
}
