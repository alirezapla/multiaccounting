package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonFilter("UserResponseFilter")
public class UserResponse {


    private String userId;
    private String username;
    //    private String password;
    @JsonFilter("AccountResponseFilter")
    private List<AccountResponse> accounts;

}
