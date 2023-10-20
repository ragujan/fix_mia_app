package com.fixmia.rag.entities.service_provider;

import com.fixmia.rag.entities.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_provider")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active_status")
    private boolean activeStatus ;

    @Column(name = "banned_status")
    private boolean bannedStatus;

    private double price;

    @Column(name = "generated_id")
    private String generatedId;

    @ManyToOne
    @JoinColumn(name = "service_provider_category_id")
    ServiceProviderCategory serviceProviderCategory;

    @OneToMany(fetch = FetchType.LAZY,mappedBy ="serviceProvider",cascade = CascadeType.REMOVE)
//    @OneToMany(mappedBy ="serviceProvider")
    private Set<ServiceProviderDescription> serviceProviderDescriptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceProvider",cascade = CascadeType.REMOVE)
//    @OneToMany( mappedBy = "serviceProvider")
    private Set<ServiceProviderPFP> serviceProviderPFPS = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public boolean isBannedStatus() {
        return bannedStatus;
    }

    public void setBannedStatus(boolean bannedStatus) {
        this.bannedStatus = bannedStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }

    public ServiceProviderCategory getServiceProviderCategory() {
        return serviceProviderCategory;
    }

    public void setServiceProviderCategory(ServiceProviderCategory serviceProviderCategory) {
        this.serviceProviderCategory = serviceProviderCategory;
    }

    public Set<ServiceProviderDescription> getServiceProviderDescriptions() {
        return serviceProviderDescriptions;
    }

    public void setServiceProviderDescriptions(Set<ServiceProviderDescription> serviceProviderDescriptions) {
        this.serviceProviderDescriptions = serviceProviderDescriptions;
    }

    public Set<ServiceProviderPFP> getServiceProviderPFPS() {
        return serviceProviderPFPS;
    }

    public void setServiceProviderPFPS(Set<ServiceProviderPFP> serviceProviderPFPS) {
        this.serviceProviderPFPS = serviceProviderPFPS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

