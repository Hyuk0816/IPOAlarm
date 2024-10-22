package com.sideprj.ipoAlarm.domain.user.entity;

import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ROLE role;



}
