package com.alikmndlu.userservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdEmailAddressDto {

    private Long id;
    private String emailAddress;
}
