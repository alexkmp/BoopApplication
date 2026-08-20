package com.boop.service.marketplace.persistence.repository;

import com.boop.service.marketplace.persistence.entity.ServiceClaim;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceClaimRepository extends CrudRepository<ServiceClaim, Long> {

    List<ServiceClaim> findAll();
}
