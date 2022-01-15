package com.alikmndlu.userservice.dto;

import com.alikmndlu.userservice.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressesDto {

    private User user;
    private AddressDto[] addresses;
}
