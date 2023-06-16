package com.roadpricing.invoice.Repo;

import com.roadpricing.invoice.Dto.PriceDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceDTORepo extends MongoRepository<PriceDto, String> {

    List<PriceDto> findAllByRouteId(String routeId);

    void deleteAllByRouteId(String routId);
}
