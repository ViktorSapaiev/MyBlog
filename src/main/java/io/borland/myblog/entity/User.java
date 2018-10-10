package io.borland.myblog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractUser implements Serializable {
    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotEmpty
    @Size(min = 8)
    @Column(nullable = false)
    private String password;
    @Transient
    private String confirmPassword;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "user_id"})})
    private Set<Role> role;

    public UserShort getUserShort() {
        UserShort userShort = new UserShort();
        userShort.setId(super.getId());
        userShort.setUsername(super.getUsername());
        return userShort;
    }
}
