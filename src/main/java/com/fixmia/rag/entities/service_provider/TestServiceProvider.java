package com.fixmia.rag.entities.service_provider;

import com.fixmia.rag.entities.User;
import com.fixmia.rag.services.ServiceProviderService;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.LoadData;

public class TestServiceProvider {
    public static void main(String[] args) {
        User user = LoadData.loadSingleData("User","email","rag@gmail.com");
        ServiceProviderCategory serviceProviderCategory = new ServiceProviderCategory();
        serviceProviderCategory.setId(1L);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setFirstName("rag");
        serviceProvider.setBannedStatus(false);
        serviceProvider.setActiveStatus(false);
        serviceProvider.setServiceProviderCategory(serviceProviderCategory);
        serviceProvider.setUser(user);


        String pfpName = "ragbag.png";
        String generatedPFPPath = ServiceProviderService.generatePFPPath(pfpName);
        ServiceProviderPFP serviceProviderPFP = new ServiceProviderPFP();
        serviceProviderPFP.setServiceProvider(serviceProvider);
        serviceProviderPFP.setPfpUrl(generatedPFPPath);
        boolean serviceProviderRegisterStatus = AddRow.addRow(serviceProvider);
        if (serviceProviderRegisterStatus) {
            boolean pfpInsertStatus = AddRow.addRow(serviceProviderPFP);
        }
    }
}
