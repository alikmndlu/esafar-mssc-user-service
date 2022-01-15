package com.alikmndlu.userservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    private Long userId;

    private String address;
}
