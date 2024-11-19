package com.ripan.production.service;

import com.ripan.production.config.JwtProvider;
import com.ripan.production.exception.UserException;
import com.ripan.production.model.User;
import com.ripan.production.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UserException("We couldn't find an account associated with the email address: " + email );

        return user;
    }
}
