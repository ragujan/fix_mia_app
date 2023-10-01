package com.fixmia.rag.entities.service_provider;

import jakarta.persistence.*;

@Entity
@Table(name = "service_provider_pfp")
public class ServiceProviderPFP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pfp_url")
    private String pfpUrl;

    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public void setPfpUrl(String pfpUrl) {
        this.pfpUrl = pfpUrl;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
