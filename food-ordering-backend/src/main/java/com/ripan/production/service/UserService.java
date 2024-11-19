package com.ripan.production.service;

import com.ripan.production.exception.UserException;
import com.ripan.production.model.User;

public interface UserService {

    // this method will find the user based on the jwt token
    User findUserByJwtToken(String jwt) throws UserException;

    User findUserByEmail(String email) throws UserException;

}
