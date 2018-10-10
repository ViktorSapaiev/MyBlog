package io.borland.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Data
abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private long id;
    @NotEmpty
    @Column(name = "username", nullable = false, unique = true)
    private String username;
}
