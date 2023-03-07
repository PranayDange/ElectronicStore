package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDao(user)).collect(Collectors.toList());
        return dtoList;
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
        return mapper.map(userDto,User.class);
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
