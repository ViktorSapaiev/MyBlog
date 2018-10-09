package io.borland.myblog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractUser implements Serializable {
    @Column
    private String email;
    @Column
    private String password;
    @Transient
    private String confirmPassword;
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
