package com.fixmia.rag.entities;

import com.fixmia.rag.entities.service_provider.ServiceProvider;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String username;
    private String contact;
    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    private String password;

    private String salt;

    @OneToMany(mappedBy = "user")
    private Set<ServiceProvider> serviceProviders = new HashSet<>();

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
