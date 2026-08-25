package com.boop.service.marketplace.persistence.repository;

import com.boop.service.marketplace.persistence.entity.ServiceReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceReviewRepository extends CrudRepository<ServiceReview, Long> {

    List<ServiceReview> findAll();
}
