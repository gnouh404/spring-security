package com.huongnguyen.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreationRequest {
    Long userId;
    String firstName;
    String lastName;
    String city;
}
