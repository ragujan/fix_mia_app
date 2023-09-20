package com.fixmia.rag.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "jti")
public class JTI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jti")
    private String JTI;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJTI() {
        return JTI;
    }

    public void setJTI(String JTI) {
        this.JTI = JTI;
    }
}
