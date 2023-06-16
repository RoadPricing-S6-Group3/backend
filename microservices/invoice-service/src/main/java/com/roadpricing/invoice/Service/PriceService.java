package com.roadpricing.invoice.Service;

import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Repo.PriceDTORepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    PriceDTORepo repo;

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    public List<PriceDto> findAll(String routeId){
        List<PriceDto> priceDtos = repo.findAllByRouteId(routeId);
        logger.info("Searched Database for PriceDTOs and found: [ " + priceDtos.size() + " ] records");
        return priceDtos;
    }

    public void savePriceDto(PriceDto priceDto){
        repo.save(priceDto);
        logger.info("Saved PriceDto to Database");
    }

    public void deleteAllByRoutId(String routeId){
        repo.deleteAllByRouteId(routeId);
        logger.info("Deleted all PriceDto by routeId in the database [ 🔥 ]");
    }
}
