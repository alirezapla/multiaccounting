package com.example.demo.controller;

import com.example.demo.dto.AccountRequest;
import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Utils;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<MappingJacksonValue> getAllUsers(@RequestParam(required = false) String username) {
        try {
            var users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //
    @GetMapping("/users/{id}")
    public ResponseEntity<MappingJacksonValue> getUserById(@PathVariable("id") String id) {
        var user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest user) {
        try {
            var userId = userService.createUser(user);
            if (userId == null) {
                return new ResponseEntity<>("Duplicate User", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") String id,
                                             @RequestBody UserRequest user) {
        var updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") String id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //
    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users/{id}/accounts")
    public ResponseEntity<List<AccountResponse>> findAccountsById(@PathVariable("id") String id) {
        try {
            var accounts = userService.findAccountsById(id);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/users/{id}/accounts")
    public ResponseEntity<String> addAccountToUser(@PathVariable("id") String id,
                                                   @RequestBody AccountRequest account) {

        var accountId = userService.addAccountToUser(id, account);
        if (accountId == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accountId, HttpStatus.CREATED);

    }

    @DeleteMapping("/users/{userId}/accounts/{accountId}")
    public ResponseEntity<String> removeAccountFromUser(@PathVariable("userId") String userId,
                                                        @PathVariable("accountId") String accountId) {

        var deletedAccount = userService.removeAccountToUser(userId, accountId);
        if (deletedAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}