package com.roadpricing.traveldata.Service;

import com.roadpricing.traveldata.Model.TravelData;
import com.roadpricing.traveldata.Repo.TravelDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelDataService {

    @Autowired
    TravelDataRepo repo;

    public TravelData findById(Long id) {
        TravelData travelData = repo.findById(id).orElse(null);
        return travelData;
    }

    public List<TravelData> findAllByRouteId(Long routeId) {
        List<TravelData> travelDataList = repo.findAllByRouteId(routeId);
        return travelDataList;
    }
}
