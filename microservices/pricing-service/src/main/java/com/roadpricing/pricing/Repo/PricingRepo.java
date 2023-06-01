package com.roadpricing.pricing.Repo;

import com.roadpricing.pricing.Model.Pricing;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//@Repository
//public interface PricingRepo extends JpaRepository<Pricing, Long> {
//
//}
//
@Repository
public interface PricingRepo extends MongoRepository<Pricing, String> {
    List<Pricing> findAllByRouteId(String id);
}
