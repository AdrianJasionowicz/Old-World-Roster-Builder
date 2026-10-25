package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.army.Army;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean isEnabled = true;

    @Column(name = "token_key", nullable = false)
    private String tokenKey = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Army> armies = new ArrayList<>();

    public void rotateTokenKey() {
        tokenKey = UUID.randomUUID().toString();
    }
}
