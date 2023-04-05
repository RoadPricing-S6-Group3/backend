package com.roadpricing.traveldata.Service;

import com.roadpricing.traveldata.Model.TravelData;
import com.roadpricing.traveldata.Repo.TravelDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    public TravelData post(TravelData travelData) {
        repo.save(travelData);
        return travelData;
    }

    public TravelData put(TravelData travelData) {
        repo.save(travelData);
        return travelData;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
