package com.foodsphere.service;

import com.foodsphere.exception.UserException;
import com.foodsphere.model.Users;

public interface UserService {

    // this method will find the user based on the jwt token
    Users findUserByJwtToken(String jwt) throws UserException;

    Users findUserByEmail(String email) throws UserException;

}
