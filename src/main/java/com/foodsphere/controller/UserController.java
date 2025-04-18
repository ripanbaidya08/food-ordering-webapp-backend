package com.foodsphere.controller;

import com.foodsphere.model.Users;
import com.foodsphere.service.UserService;
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
    public ResponseEntity<Users> findUserByJwtToken(@RequestHeader ("Authorization") String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<Users> findUserByEmail(@PathVariable String email) throws Exception {
        Users user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
