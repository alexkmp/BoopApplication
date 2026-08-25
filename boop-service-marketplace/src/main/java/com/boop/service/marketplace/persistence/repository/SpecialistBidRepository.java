package com.boop.service.marketplace.persistence.repository;

import com.boop.service.marketplace.persistence.entity.SpecialistBid;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpecialistBidRepository extends CrudRepository<SpecialistBid, Long> {

    List<SpecialistBid> findAll();
}
