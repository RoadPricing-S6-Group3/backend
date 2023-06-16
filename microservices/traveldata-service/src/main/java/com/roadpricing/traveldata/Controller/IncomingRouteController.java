package com.roadpricing.traveldata.Controller;

import com.roadpricing.traveldata.Service.RouteInfoService;
import com.roadpricing.traveldata.dto.IncomingRouteDTO;
import com.roadpricing.traveldata.dto.OutGoingRouteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class IncomingRouteController {

    @Autowired
    RouteInfoService routeInfoService;

    private static final Logger logger = LoggerFactory.getLogger(IncomingRouteController.class);
    @PostMapping("/submit-raw/{cc}")
    public ResponseEntity<List<OutGoingRouteDTO>> retrieveRoute(@RequestBody IncomingRouteDTO incoming, @PathVariable(value = "cc")String cc){
        logger.info("Received incoming raw-route from country: [ " + cc + " ] [ 🗺️ ]" );
        IncomingRouteDTO recieved = incoming;
        List<OutGoingRouteDTO> outs = routeInfoService.sendProcessRoute(recieved, cc);
        logger.info("Finished processing raw-route from country: [ " + cc + " ] [ 🏁 ]" );
        return ResponseEntity.ok().body(outs);
    }
}
