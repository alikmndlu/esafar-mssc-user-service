package com.alikmndlu.userservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCredentialsDto {

    private String name;
    private String emailAddress;
    private String password;
}
