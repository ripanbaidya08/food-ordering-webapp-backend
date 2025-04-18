package com.foodsphere.service;

import com.foodsphere.config.JwtProvider;
import com.foodsphere.exception.UserException;
import com.foodsphere.model.Users;
import com.foodsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public Users findUserByJwtToken(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Users user = findUserByEmail(email);

        return user;
    }

    @Override
    public Users findUserByEmail(String email) throws UserException {
        Users user = userRepository.findByEmail(email);
        if(user == null) throw new UserException("We couldn't find an account associated with the email address: " + email );

        return user;
    }
}
