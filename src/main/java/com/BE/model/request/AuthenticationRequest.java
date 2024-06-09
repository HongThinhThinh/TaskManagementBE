package com.BE.model.request;

import com.BE.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    String firstName;
    String lastName;
    String username;
    String password;
    RoleEnum role;
}
