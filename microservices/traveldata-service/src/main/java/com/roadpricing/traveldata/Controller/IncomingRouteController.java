package com.roadpricing.traveldata.Controller;

import com.roadpricing.traveldata.Service.RouteInfoService;
import com.roadpricing.traveldata.dto.IncomingRouteDTO;
import com.roadpricing.traveldata.dto.OutGoingRouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class IncomingRouteController {

    @Autowired
    RouteInfoService routeInfoService;
    @PostMapping("/submit-raw")
    public ResponseEntity<List<OutGoingRouteDTO>> retrieveRoute(@RequestBody IncomingRouteDTO incoming){

        IncomingRouteDTO recieved = incoming;
        List<OutGoingRouteDTO> outs = routeInfoService.sendProcessRoute(recieved);
        return ResponseEntity.ok().body(outs);
    }
}
