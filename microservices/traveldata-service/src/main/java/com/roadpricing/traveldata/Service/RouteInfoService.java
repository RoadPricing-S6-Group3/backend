package com.roadpricing.traveldata.Service;

import com.roadpricing.traveldata.dto.IncomingRouteDTO;
import com.roadpricing.traveldata.dto.OutGoingRouteDTO;
import com.roadpricing.traveldata.dto.PointDTO;
import com.roadpricing.traveldata.dto.VehicleDTO;
import com.roadpricing.traveldata.rabbitmq.Publisher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteInfoService {
    @Autowired
    Publisher publisher;
    Logger logger = LoggerFactory.getLogger(RouteInfoService.class);
    public List<OutGoingRouteDTO> sendProcessRoute(IncomingRouteDTO incomingRouteDTO, String countryCode){
        //Sorts the list by data
        incomingRouteDTO.getPoints().sort((d1,d2) -> d1.compareTo(d2));
        List<String> coordsToSend = createCoordsToSend(incomingRouteDTO.getPoints());
        List<String> definitiveCoords = createDefinitiveCoords(coordsToSend);
        OutGoingRouteDTO out = new OutGoingRouteDTO();
        List<OutGoingRouteDTO> outGoingRouteDTOS = createListOutGoingDTOs(definitiveCoords, incomingRouteDTO.getVehicle(), countryCode, incomingRouteDTO.getPoints());
        logger.info("route processed");
        return outGoingRouteDTOS;
    }

    //Method to Retrieve Route info
    private OutGoingRouteDTO retrieveRouteDataFromOSRM(String coord) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String body = null;
        String url = "http://router.project-osrm.org/route/v1/car/"+ coord +"?geometries=geojson&overview=full&steps=true";
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                body = response.getBody();
            }
            else {
                // Handle error cases
                throw new Exception("Error occurred: " + response.getStatusCode());
            }
        }finally {
            restTemplate.getInterceptors().clear();
            restTemplate.getMessageConverters().clear();
            restTemplate = null;
        }
        return convertResponseToOutgoingDTO(body);
    }
    //Method that creates coord Strings from the incomingDTO
    private List<String> createCoordsToSend(List<PointDTO> pointDTOS){
        List<String> coordsToSend = new ArrayList<>();
        for (PointDTO point: pointDTOS) {
            StringBuilder sb = new StringBuilder();
            sb.append(point.getLat()).append(",").append(point.getLon());
            coordsToSend.add(sb.toString());
        }
        return coordsToSend;
    }

    //Method that creates the definitive coords to send
    private List<String> createDefinitiveCoords(List<String> coords){
        List<String> definitiveCoords = new ArrayList<>();
        for (int i = 0; i + 1 < coords.size(); i += 1){
            StringBuilder sb = new StringBuilder();
            sb.append(coords.get(i)).append(";").append(coords.get(i + 1));
//            String startendcoord;
//            startendcoord = coords.get(i);
//            startendcoord = startendcoord + ";" + coords.get(i + 1);
            definitiveCoords.add(sb.toString());
        }
        return definitiveCoords;
    }

    //Methods to form OutgoingDTO
    private List<OutGoingRouteDTO> createListOutGoingDTOs(List<String> defeniticeCoords, VehicleDTO vehicleDTO, String countryCode, List<PointDTO> points){
        List<OutGoingRouteDTO> outGoingRouteDTOS = new ArrayList<>();
        int addCount = 0;
        for(String coords : defeniticeCoords){
            try{
                OutGoingRouteDTO out = new OutGoingRouteDTO();
                out = retrieveRouteDataFromOSRM(coords);
                out.setCountryCode(countryCode);
                if(out.getDistance() != null){
                    out.setFuelType(vehicleDTO.getFuelType());
                    out.setVehicleType(vehicleDTO.getVehicleClassification());
                    out.setRouteId(vehicleDTO.getId());
                }
                if( addCount + 1 < defeniticeCoords.size()){
                    out.setInProgress(true);

                    PointDTO pointStart = points.get(addCount);
                    PointDTO pointEnd = points.get(addCount + 1);

                    String startLat = pointStart.getLat();
                    String startLon = pointStart.getLon();

                    String endLat = pointEnd.getLat();
                    String endLon = pointEnd.getLon();

                    out.setStartLat(Double.parseDouble(startLat));
                    out.setStartLon(Double.parseDouble(startLon));

                    out.setEndLat(Double.parseDouble(endLat));
                    out.setEndLon(Double.parseDouble(endLon));
                    out.setTime(pointStart.getTime().toString());
                    addCount ++;
                } else if (addCount + 1 == defeniticeCoords.size()) {
                    PointDTO pointStart = points.get(addCount);
                    PointDTO pointEnd = points.get(addCount + 1);

                    String startLat = pointStart.getLat();
                    String startLon = pointStart.getLon();

                    String endLat = pointEnd.getLat();
                    String endLon = pointEnd.getLon();

                    out.setStartLat(Double.parseDouble(startLat));
                    out.setStartLon(Double.parseDouble(startLon));

                    out.setEndLat(Double.parseDouble(endLat));
                    out.setEndLon(Double.parseDouble(endLon));

                    out.setTime(pointStart.getTime().toString());
                    out.setInProgress(false);
                }
                outGoingRouteDTOS.add(out);
                publisher.publishOutGoingRouteDTO(out);
            }
            catch (Exception e){
                logger.error("Error: " + e);
            }
        }
        return outGoingRouteDTOS;
    }
    //Methods to read info from the JSON
    private OutGoingRouteDTO convertResponseToOutgoingDTO(String response){
        JSONObject jsonObject = new JSONObject(response);
        JSONArray routes = (JSONArray) jsonObject.get("routes");
        Object obj = routes.get(0);

        Map<String, Object> infoMap = createInfoMap(obj);
        Map<String, String> roadInfo = readRoadNameAndTypeFromJSON(infoMap);

        OutGoingRouteDTO outGoingRouteDTO = createRoute(infoMap);
        outGoingRouteDTO.setRoadName(roadInfo.get("roadName"));
        outGoingRouteDTO.setRoadType(roadInfo.get("roadType"));

        return outGoingRouteDTO;
    }
    private Map<String, Object> createInfoMap(Object obj){
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject(obj.toString());
        map.put("duration", jsonObject.get("duration"));
        map.put("distance", jsonObject.get("distance"));
        map.put("legs", jsonObject.get("legs"));
        map.put("weight_name", jsonObject.get("weight_name"));
        map.put("weight", jsonObject.get("weight"));
        map.put("geometry", jsonObject.get("geometry"));
        return map;
    }
    private Map<String, String> readRoadNameAndTypeFromJSON(Map<String, Object> infoMap){
        Map<String, String> roadInfo = new HashMap<>();
        String roadType = "N/A";
        String roadName = "N/A";
        JSONArray legs = (JSONArray) infoMap.get("legs");
        JSONObject road = legs.getJSONObject(0);
        JSONObject steps = legs.getJSONObject(0);
        JSONArray steps2 = steps.getJSONArray("steps");
        JSONObject steps3 = steps2.getJSONObject(0);
        if (steps3.has("ref")) {
            roadType = steps3.getString("ref");
        } else {
            logger.error("Could not find 'ref' key");
        }
        if (road.has("summary")) {
            roadName = road.getString("summary");
        } else {
            logger.error("Could not find 'summary' key");
        }
        if(roadName.isBlank() || roadName.isEmpty() || roadName.equals(null)){
            roadInfo.put("roadName", "N/A");
        }else{
            roadInfo.put("roadName", roadName);
        }
        if(roadType.isBlank() || roadType.isEmpty()|| roadType.equals(null)){
            roadInfo.put("roadType", "N/A");
        }
        else {
            roadInfo.put("roadType", roadType);
        }
        return roadInfo;
    }

    private OutGoingRouteDTO createRoute(Map<String,Object> mapInfo){
        OutGoingRouteDTO outGoingRouteDTO = new OutGoingRouteDTO();

        Object distanceObj = mapInfo.get("distance");
        if (distanceObj instanceof BigDecimal) {
            outGoingRouteDTO.setDistance((BigDecimal) distanceObj);
        } else if (distanceObj instanceof Integer) {
            outGoingRouteDTO.setDistance(BigDecimal.valueOf((Integer) distanceObj));
        } else if (distanceObj instanceof Long) {
            outGoingRouteDTO.setDistance(BigDecimal.valueOf((Long) distanceObj));
        } else {
            // Handle the case when the distance value is of an unexpected type
            throw new IllegalArgumentException("Invalid distance value");
        }
        return outGoingRouteDTO;
    }
}
