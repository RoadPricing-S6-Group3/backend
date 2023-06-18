package com.roadpricing.tracking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.tracking.dto.OutGoingRouteDTO;
import com.roadpricing.tracking.dto.PointDTO;
import com.roadpricing.tracking.dto.VehicleDTO;
import com.roadpricing.tracking.model.RouteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class CreateOutGoingService {

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CreateOutGoingService.class);

    public OutGoingRouteDTO createOutGoingRouteDTO(RouteModel routeModel){
        OutGoingRouteDTO outGoingRouteDTO = new OutGoingRouteDTO();
        List<PointDTO> points = new ArrayList<>();
        VehicleDTO vehicleDTO = new VehicleDTO();
        UUID id = UUID.randomUUID();
        vehicleDTO.setId(id.toString());
        vehicleDTO.setFuelType("Petrol");
        vehicleDTO.setVehicleClassification("L2");
        java.util.Map<Integer, String> coords = routeModel.getCoords();
        Calendar currentTime = Calendar.getInstance();
        currentTime.

        int counter = coords.size();
        Date time = new Date();
        for(int i = 0; i < counter; i++){
            String coord = coords.get(i);
            String[] parts = coord.split(";");
            PointDTO pointDTO = new PointDTO();
            pointDTO.setLat(parts[0]);
            pointDTO.setLon(parts[1]);
            pointDTO.setTime(currentTime.getTime());
            points.add(pointDTO);
            currentTime.add(Calendar.MINUTE, 1);
        }
        outGoingRouteDTO.setPoints(points);
        outGoingRouteDTO.setVehicle(vehicleDTO);
        return outGoingRouteDTO;
    }

    public String createSubmitRaw(OutGoingRouteDTO dto){
        String submitRaw = "";
        try {
            submitRaw = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return submitRaw;
    }
    public String sendToTravelData(OutGoingRouteDTO dto, String cc){
        String toReturn = "";
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String url = createUrlForCountry(cc);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OutGoingRouteDTO> outGoingRouteDTOHttpEntity = new HttpEntity<>(dto, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, outGoingRouteDTOHttpEntity, String.class);
            String received = response.toString();
            logger.info(received);
            if(response.getStatusCode().is2xxSuccessful()){
                toReturn = objectMapper.writeValueAsString(dto);
            }
            else{
                toReturn = response.getStatusCode().toString();
            }
        }
        catch (Exception e){
            logger.info("Error: " + e);
        }
        return toReturn;
    }
    private String createUrlForCountry(String cc){

        if(cc.equals("nl") || cc.equals("NL")){
            String url = "http://34.140.232.108/api/route/submit-raw?cc=" + cc.toUpperCase();
            return url;
        }
        else if(cc.equals("be") || cc.equals("BE")){
            String  url = "https://international.oibss.nl/submit-raw?cc="+cc.toUpperCase();
            return url;
        }
        else if (cc.equals("lux") || cc.equals("LUX") || cc.equals("lu") || cc.equals("LU")){
            String  url = "http://34.159.70.126/api/submit-raw?cc="+cc.toUpperCase();
            return url;
        }
        else {
            String url = "N/A";
            return url;
        }
    }
}
