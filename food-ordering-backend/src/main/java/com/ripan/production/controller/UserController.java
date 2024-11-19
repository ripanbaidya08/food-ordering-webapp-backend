package com.ripan.production.controller;

import com.ripan.production.model.User;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * we need to provide the token, if we want to perform any operation wrt user means the customer, and this step will follow
     * throughout the program.
     * @param jwt
     * @return
     * @throws Exception
     */
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader ("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) throws Exception {
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
