package com.roadpricing.traveldata.Controller;

import com.roadpricing.traveldata.Service.RouteInfoService;
import com.roadpricing.traveldata.dto.IncomingRouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class IncomingRouteController {

    @Autowired
    RouteInfoService routeInfoService;
    @PostMapping("/submit-raw")
    public ResponseEntity<IncomingRouteDTO> retrieveRoute(@RequestBody IncomingRouteDTO incoming){

        IncomingRouteDTO recieved = incoming;
        routeInfoService.sendProcessRoute(recieved);

        return ResponseEntity.ok().body(recieved);

    }
}
