package com.stelios.ServiceLink.repository;

import com.stelios.ServiceLink.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stelios.ServiceLink.entity.Service; // This is CORRECT

import java.util.List;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    /**
     * Finds all providers that are associated with a specific service.
     * @param service The Service entity to filter by.
     * @return A list of matching ServiceProviders.
     */
    List<ServiceProvider> findByServicesContaining(Service service);
}