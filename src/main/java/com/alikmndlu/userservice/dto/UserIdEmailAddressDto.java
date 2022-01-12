package com.alikmndlu.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdEmailAddressDto {

    private Long id;
    private String emailAddress;
}
