package com.sideprj.ipoAlarm.domain.user.entity;

import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role;



}
