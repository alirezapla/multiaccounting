package com.example.demo.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Account> accounts;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", username=" + username + ", password=" + password  + "]";
    }

}