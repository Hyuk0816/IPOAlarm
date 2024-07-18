package com.sideprj.ipoAlarm.domain.user.role;

import lombok.Getter;

@Getter
public enum ROLE {

    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    ROLE(String name) {
        this.name = name;
    }
}
