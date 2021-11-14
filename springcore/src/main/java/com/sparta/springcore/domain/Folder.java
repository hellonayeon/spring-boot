package com.sparta.springcore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Folder extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    // User 객체의 관점에서는 @OneToMany
    // Folder 객체의 관점에서는 @ManyToOne
    // @[현재클래스]To[대상클래스]
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
