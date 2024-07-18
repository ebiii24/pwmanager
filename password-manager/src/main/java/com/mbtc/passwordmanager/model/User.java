package com.mbtc.passwordmanager.model;

import com.mbtc.passwordmanager.util.Generator;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @OneToMany
    private List<UrlCredential> urlCredentials;

    public User(){};

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(Integer id, String username, String password) {
        this.username = username;
        this.password = Generator.generatePW(10);
    }
}
