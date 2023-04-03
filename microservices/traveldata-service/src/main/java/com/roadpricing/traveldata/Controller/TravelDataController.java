package com.roadpricing.traveldata.Controller;

import com.roadpricing.traveldata.Model.TravelData;
import com.roadpricing.traveldata.Service.TravelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/traveldata")
public class TravelDataController {

    @Autowired
    TravelDataService service;

    @GetMapping("/id/{id}")
    public ResponseEntity<TravelData> getTravelDataById(@PathVariable(value = "id")Long id){
        try{
            TravelData travelData = service.findById(id);
            return ResponseEntity.ok().body(travelData);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<TravelData>> getVehicleByOwnerId(@PathVariable(value = "routeId")Long routeId){
        try{
            List<TravelData> travelDataList = service.findAllByRouteId(routeId);
            return ResponseEntity.ok().body(travelDataList);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
