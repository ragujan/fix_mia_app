package com.fixmia.rag.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "jti")
public class JTI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jti")
    private String JTIClaim;

    public JTI() {
    }

    public JTI(String JTIClaim) {
        this.JTIClaim = JTIClaim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJTIClaim() {
        return JTIClaim;
    }

    public void setJTIClaim(String JTIClaim) {
        this.JTIClaim = JTIClaim;
    }
}
