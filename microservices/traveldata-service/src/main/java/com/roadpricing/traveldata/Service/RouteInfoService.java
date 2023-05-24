package com.roadpricing.traveldata.Service;

import com.roadpricing.traveldata.dto.IncomingRouteDTO;
import com.roadpricing.traveldata.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteInfoService {

    Logger logger = LoggerFactory.getLogger(RouteInfoService.class);
    public void sendProcessRoute(IncomingRouteDTO incomingRouteDTO){
        incomingRouteDTO.getPoints().sort((d1,d2) -> d1.compareTo(d2));
        List<String> coordsTosend = new ArrayList<>();
        String test = "";
        for (PointDTO point: incomingRouteDTO.getPoints()) {
            coordsTosend.add(point.getLat()+";"+point.getLon());
        }
        for (String coord: coordsTosend) {
            test = test + coord;
        }
        try{
            retrieveRouteDataFromOSRM(test);
        }
        catch (Exception e){
            logger.error("Error: " + e);
        }
    }

    private void retrieveRouteDataFromOSRM(String coord) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String body = null;
        String url = "http://router.project-osrm.org/route/v1/car/"+coord+"?geometries=geojson&overview=full";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            body = response.getBody();
        }
        else {
            // Handle error cases
            throw new Exception("Error occurred: " + response.getStatusCode());
        }

    }
}
