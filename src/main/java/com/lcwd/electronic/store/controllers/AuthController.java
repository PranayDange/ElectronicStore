package com.lcwd.electronic.store.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.lcwd.electronic.store.dtos.JwtRequest;
import com.lcwd.electronic.store.dtos.JwtResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.security.JwtHelper;
import com.lcwd.electronic.store.services.UserService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(value = "AuthController",description = "API for Authentication!!")

//corsorigin by using options
/*@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = {"Authorization"},
        methods = {RequestMethod.GET,RequestMethod.POST},
        maxAge = 3600
)*/
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Value("${googleClientId}")
    private String googleClientID;
    @Value("${newPassword}")
    private String newPassword;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadApiRequestException e) {
            throw new BadApiRequestException("Invalid Username or Password !!");
        }
    }


    @GetMapping("/current")
    public ResponseEntity<UserDetails> getCurrentUser(Principal principal) {
        //principlr is used to get current user object (isse pata chalenga konsa user login hai)
        //principle ke object se username fetch karenge
        String name = principal.getName();
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(name), HttpStatus.OK);

    }


    //login with google
    @PostMapping("/google")
    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Object> data) throws IOException {

        //get the id token from request
        String idToken = data.get("idToken").toString();

        NetHttpTransport netHttpTransport = new NetHttpTransport();
        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientID));

        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        logger.info("payload : {}", payload);

        String email = payload.getEmail();

        User user = null;

        user = userService.findUserByEmailOptional(email).orElse(null);

        if (user == null) {
            //create  new user
            user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString());
        }

        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder()
                .email(user.getEmail())
                .password(newPassword)
                .build());

        return jwtResponseResponseEntity;


    }

    private User saveUser(String email, String name, String photoUrl) {

        UserDto newUser = UserDto.builder()
                .name(name)
                .email(email)
                .password(newPassword)
                .imageName(photoUrl)
                .roles(new HashSet<>())
                .build();

        UserDto user = userService.createUser(newUser);

        return this.mapper.map(user, User.class);
    }
}
