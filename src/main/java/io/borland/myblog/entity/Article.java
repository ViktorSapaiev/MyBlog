package io.borland.myblog.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserShort author;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;
    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, unique = true)
    private String header;
    @NotBlank
    @Column(nullable = false)
    private String text;
}
