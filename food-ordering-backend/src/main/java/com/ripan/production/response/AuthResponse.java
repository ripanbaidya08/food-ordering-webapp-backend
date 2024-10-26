package com.ripan.production.response;

import com.ripan.production.model.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;
}
