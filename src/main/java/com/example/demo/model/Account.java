package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "account")
public class Account {


    @Id
    private String id;

    @Setter
    @Column(name = "accountName")
    private String accountName;

    @Setter
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {

    }

    public Account(String accountName, String email, User user) {
        this.id = UUID.randomUUID().toString();
        this.accountName = accountName;
        this.email = email;
        this.user = user;
    }

}