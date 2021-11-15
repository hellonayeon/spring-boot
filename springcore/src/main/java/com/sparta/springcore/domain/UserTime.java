package com.sparta.springcore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private long totalTime;

    public UserTime(User user, long totalTime) {
        this.user = user;
        this.totalTime = totalTime;
    }

    public void updateTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
