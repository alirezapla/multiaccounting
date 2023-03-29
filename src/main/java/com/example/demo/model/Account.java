package com.example.demo.model;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "account")
public class Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Setter
    @Column(name = "accountName")
    private String accountName;

    @Setter
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Account() {

    }

    public Account(String accountName , String email, User user){
        this.accountName = accountName;
        this.email = email;
        this.user = user;
    }

}