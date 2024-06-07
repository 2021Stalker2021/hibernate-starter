package com.dmdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // этот id должен полностью совподать с id нашего
    // пользователя (поле user) если мы не используем автогенерируемый ключ

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String street;

    private String language;

    public void setUser(User user) {
        this.user = user;
//        this.user.setProfile(this);
    }
}
