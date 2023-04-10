package com.example.demo.service;

import com.example.demo.Utils;
import com.example.demo.dto.AccountRequest;
import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepository;
    private final AccountRepo accountRepository;


    public UserService(UserRepo userRepository, AccountRepo accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public String createUser(UserRequest user) {
        Optional<User> usersData = userRepository.findByUsername(user.getUsername());
        if (usersData.isPresent()) {
            return null;
        } else {
            User _user = userRepository
                    .save(new User(user.getUsername(), user.getPassword()));
            return _user.getId();
        }
    }

    public MappingJacksonValue getAllUsers() {
        List<UserResponse> usersResponse = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserResponse userResponse = UserResponse.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .build();
            usersResponse.add(userResponse);
        }
        return Utils.filterModel(usersResponse, "username", "userId");
    }

    public MappingJacksonValue getUserById(String id) {
        Optional<User> usersData = userRepository.findById(id);
        return getUserResponse(usersData);
    }

    public MappingJacksonValue getUserByUsername(String username) {
        Optional<User> usersData = userRepository.findByUsername(username);
        return getUserResponse(usersData);

    }
    private MappingJacksonValue getUserResponse(Optional<User> usersData) {
        if (usersData.isEmpty()) {
            return null;
        } else {
            User _user = usersData.get();
            var accounts = _user.getAccounts().stream()
                    .map(a -> new AccountResponse(a.getAccountName(), a.getEmail(), a.getId()))
                    .collect(Collectors.toList());
            return Utils.filterModel(UserResponse.builder()
                    .username(_user.getUsername())
                    .accounts(accounts)
                    .build(), "username", "accounts");
        }
    }



    public String updateUser(String id, UserRequest user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setPassword(user.getPassword());
            _user.addAccounts((Account) user.getAccounts());
            return _user.getId();
        } else {
            return null;
        }
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<AccountResponse> findAccountsById(String id) {
        Optional<User> userData = userRepository.findById(id);
        List<AccountResponse> accounts = new ArrayList<>();
        if (userData.isPresent()) {
            User _user = userData.get();
            for (Account account : _user.getAccounts()) {
                AccountResponse _account = AccountResponse.builder()
                        .accountName(account.getAccountName())
                        .email(account.getEmail())
                        .accountId(account.getId())
                        .build();
                accounts.add(_account);
            }
        }
        return accounts;

    }

    public String addAccountToUser(String id, AccountRequest account) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            Account newAccount = accountRepository.save(new Account(account.getAccountName()
                    , account.getEmail(), _user));

            _user.addAccounts(newAccount);
            return newAccount.getId();
        }
        return null;
    }

    public String removeAccountToUser(String userId, String accountId) {
        Optional<User> userData = userRepository.findById(userId);
        if (userData.isPresent()) {
            if (accountRepository.existsById(accountId)) {
                accountRepository.deleteById(accountId);
                return "OK";
            }
            return null;
        }
        return null;
    }

}
