package com.BE.model.response;


import com.BE.enums.RoleEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    String token;
    String username;
    RoleEnum role;
    String firstname;
    String lastname;

}

