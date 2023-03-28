package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private Role role;
    private User user;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void beforeEach() {
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

    }

    @Test
    public void createUserTest() throws Exception {

        //   /users + post  + user data as json
        //data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    @Test
    public void updateUserTest() throws Exception {

        //   /users/{userId} + put  + user data as json
        String userId = "123";
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/" + userId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer sffkuehfwhflofwelfjwlf")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());


    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
