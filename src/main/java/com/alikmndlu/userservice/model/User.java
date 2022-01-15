package com.alikmndlu.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email_adddress", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;
}
