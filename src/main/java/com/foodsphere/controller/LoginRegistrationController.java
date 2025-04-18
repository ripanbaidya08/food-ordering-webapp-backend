package com.foodsphere.controller;

import com.foodsphere.config.JwtProvider;
import com.foodsphere.exception.UserException;
import com.foodsphere.model.Cart;
import com.foodsphere.model.USER_ROLE;
import com.foodsphere.model.Users;
import com.foodsphere.repository.CartRepository;
import com.foodsphere.repository.UserRepository;
import com.foodsphere.request.LoginRequest;
import com.foodsphere.response.AuthResponse;
import com.foodsphere.service.CustomerUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginRegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final CustomerUserDetailsService customerUserDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody Users user) throws Exception {

        // check whether user exist or not.
        Users isExist = userRepository.findByEmail(user.getEmail());
        if(isExist != null) throw new UserException("Email already in use. Please use a different email.");

        Users createdUserRequest = new Users();

        createdUserRequest.setEmail(user.getEmail());
        createdUserRequest.setFullName(user.getFullName());
        createdUserRequest.setRole(user.getRole());
        createdUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));

        // save the user details to the database.
        Users persistedUser = userRepository.save(createdUserRequest);

        Cart createdCartRequest = new Cart();
        createdCartRequest.setCustomer(persistedUser);
        cartRepository.save(createdCartRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // after the registration, it need to generate the token.
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "Your account has been created successfully", persistedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {

        // first, authenticate the user
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        Users user = userRepository.findByEmail(request.getEmail());

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "Welcome, "+user.getFullName()+" You are now logged in.", USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // authenticate the user by there username and password.
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if(userDetails == null) throw new BadCredentialsException("User not found!");

        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Incorrect password!");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
