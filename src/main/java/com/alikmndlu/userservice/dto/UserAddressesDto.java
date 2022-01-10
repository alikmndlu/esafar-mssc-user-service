package com.alikmndlu.userservice.dto;

import com.alikmndlu.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressesDto {

    private User user;

    private AddressDto[] addresses;
}
