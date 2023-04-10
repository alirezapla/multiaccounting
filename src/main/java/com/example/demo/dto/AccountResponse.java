package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonFilter("AccountResponseFilter")
public class AccountResponse {

    private String accountName;
    private String email;
    private String accountId;
}
