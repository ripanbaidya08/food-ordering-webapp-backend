package com.ripan.production.controller;

import com.ripan.production.config.JwtProvider;
import com.ripan.production.exception.UserException;
import com.ripan.production.model.Cart;
import com.ripan.production.model.USER_ROLE;
import com.ripan.production.model.User;
import com.ripan.production.repository.CartRepository;
import com.ripan.production.repository.UserRepository;
import com.ripan.production.request.LoginRequest;
import com.ripan.production.response.AuthResponse;
import com.ripan.production.service.CustomerUserDetailsService;
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
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {

        // check whether user exist or not.
        User isExist = userRepository.findByEmail(user.getEmail());
        if(isExist != null) throw new UserException("Email already in use. Please use a different email.");

        User createdUserRequest = new User();

        createdUserRequest.setEmail(user.getEmail());
        createdUserRequest.setFullName(user.getFullName());
        createdUserRequest.setRole(user.getRole());
        createdUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));

        // save the user details to the database.
        User persistedUser = userRepository.save(createdUserRequest);

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

        User user = userRepository.findByEmail(request.getEmail());

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
