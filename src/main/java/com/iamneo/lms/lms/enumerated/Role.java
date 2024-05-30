package com.iamneo.lms.lms.enumerated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ADMIN(
        Set.of(
            Permission.ADMIN_CREATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            
            Permission.TEACHER_CREATE,
            Permission.TEACHER_DELETE,
            Permission.TEACHER_READ,
            Permission.TEACHER_UPDATE,
            
            Permission.STUDENT_CREATE,
            Permission.STUDENT_DELETE,
            Permission.STUDENT_READ,
            Permission.STUDENT_UPDATE
        )
    ),

    TEACHER(
        Set.of(
            Permission.TEACHER_CREATE,
            Permission.TEACHER_DELETE,
            Permission.TEACHER_READ,
            Permission.TEACHER_UPDATE
        )
    ),

    STUDENT(
        Set.of(
            Permission.STUDENT_CREATE,
            Permission.STUDENT_DELETE,
            Permission.STUDENT_READ,
            Permission.STUDENT_UPDATE
        )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                            .stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                            .collect(Collectors.toList());
        
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
