package com.iamneo.lms.lms.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update"),
    
    TEACHER_READ("teacher:read"),
    TEACHER_CREATE("teacher:create"),
    TEACHER_DELETE("teacher:delete"),
    TEACHER_UPDATE("teacher:update"),
    
    STUDENT_READ("student:read"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete"),
    STUDENT_UPDATE("student:update");

    @Getter
    private final String permission;

}
