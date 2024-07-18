package com.mbtc.passwordmanager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name="Url_Credentials")
@Getter
@Setter
public class UrlCredential {
    private Integer id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer credId;
    private String url;
    private String urlPassword;
}

