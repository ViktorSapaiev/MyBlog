package io.borland.myblog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserShort extends AbstractUser {
}
