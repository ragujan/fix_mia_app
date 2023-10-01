package com.fixmia.rag.entities.service_provider;

import com.fixmia.rag.services.ServiceProviderPFPService;
import com.fixmia.rag.util.hibernate.AddRow;

public class TestServiceProvider {
    public static void main(String[] args) {
        ServiceProviderCategory serviceProviderCategory = new ServiceProviderCategory();
        serviceProviderCategory.setId(1L);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setFirstName("rag");
        serviceProvider.setBannedStatus(false);
        serviceProvider.setActiveStatus(false);
        serviceProvider.setServiceProviderCategory(serviceProviderCategory);


        String pfpName = "ragbag.png";
        String generatedPFPPath = ServiceProviderPFPService.generatePFPPath(pfpName);
        ServiceProviderPFP serviceProviderPFP = new ServiceProviderPFP();
        serviceProviderPFP.setServiceProvider(serviceProvider);
        serviceProviderPFP.setPfpUrl(generatedPFPPath);
        boolean serviceProviderRegisterStatus = AddRow.addRow(serviceProvider);
        if (serviceProviderRegisterStatus) {
            boolean pfpInsertStatus = AddRow.addRow(serviceProviderPFP);
        }
    }
}
