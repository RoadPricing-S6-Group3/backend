package com.roadpricing.traveldata.Repo;

import com.roadpricing.traveldata.Model.TravelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelDataRepo extends JpaRepository<TravelData, Long> {
    Optional<TravelData> findById(Long id);

    List<TravelData> findAllByRouteId(Long ownerId);
}
