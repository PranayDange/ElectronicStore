package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;
    Role role;
    String roleId;

    @BeforeEach
    public void init() {

        role = Role.builder()
                .roleId("abs")
                .roleName("NORMAL")
                .build();

        user = User.builder()
                .name("pranay")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        roleId = "abc";

    }

    //create user
    @Test
    public void createUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("pranay", user1.getName());

    }

    //update user test
    @Test
    public void updateUserTest() {
        String userId = "sfegegsg";
        UserDto userDto = UserDto.builder()
                .name("pranay")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);
        System.out.println(updateUser.getName());
        System.out.println(updateUser.getImageName());

        Assertions.assertNotNull(userDto);

    }

    //delete user test case
    @Test
    public void deleteUserTest() {
        String userId = "userABC";
        Mockito.when(userRepository.findById("userABC")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    //get all user test case
    @Test
    public void getAllUsersTest() {
        User user1 = User.builder()
                .name("sandesh")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        User user2 = User.builder()
                .name("shubham")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

       /* Sort sort =  Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(1,2, sort);*/

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3, allUser.getContent().size());

    }

    @Test
    public void getUserByIdTest() {
        String userId = "userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //actual call of service method
        UserDto userDto = userService.getUserByID(userId);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Name not matched!!");

    }

    @Test
    public void getUserByEmailTest() {
        String emailId = "pranay@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        //actual call of service method
        UserDto userDto = userService.getUserByEmail(emailId);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(), userDto.getEmail(), "email not matched!!");


    }

    @Test
    public void searchUSerTest() {
        User user1 = User.builder()
                .name("sandesh")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        User user2 = User.builder()
                .name("shubham")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        User user3 = User.builder()
                .name("pranay")
                .email("pranay@gmail.com")
                .about("this is testing create method of user")
                .gender("male")
                .imageName("abs.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();

        String keywords = "pranay";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user1, user2, user3));

        List<UserDto> userDtos = userService.searchUser(keywords);

        Assertions.assertEquals(3, userDtos.size(), "size not matched!!");


    }


    @Test
    public void findUserByemailOptionalTest() {

        String email = "pranay@gmail.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> userByEmailOptional = userService.findUserByEmailOptional(email);
        Assertions.assertTrue(userByEmailOptional.isPresent());

        User user1 = userByEmailOptional.get();
        Assertions.assertEquals(user.getEmail(), user1.getEmail(), "email does not match!!");

    }


}
