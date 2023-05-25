package com.roadpricing.tracking.service;

import com.roadpricing.tracking.model.RouteModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RouteService {
    public RouteModel createRoute(String coordinates) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String body = null;
        String url = "http://router.project-osrm.org/route/v1/car/" + coordinates + "?geometries=geojson&overview=full&steps=true";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            body = response.getBody();
        }
        else {
            // Handle error cases
           throw new Exception("Error occurred: " + response.getStatusCode());
        }
        return createModelFromResponse(body);
    }

    //Private methods
    private RouteModel createModelFromResponse(String response){
        JSONObject jsonObject = new JSONObject(response);
        JSONArray routes = (JSONArray) jsonObject.get("routes");
        Object obj = routes.get(0);

        Map<String, Object> infoMap = createInfoMap(obj);
        JSONObject geometry = (JSONObject) infoMap.get("geometry");
        JSONArray coordinates = geometry.getJSONArray("coordinates");
        Map<Integer, String> coords = getCoordinates(coordinates);
        RouteModel routeModel = createRoute(infoMap, coords);
        return routeModel;
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
    private Map<Integer, String> getCoordinates(JSONArray jsonArray){
        Map<Integer, String> map = new HashMap<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONArray index = jsonArray.getJSONArray(i);
            BigDecimal longitude = index.getBigDecimal(0);
            BigDecimal latitude = index.getBigDecimal(1);
            String coords = longitude.toString() + ";" + latitude.toString();
            map.put(i,coords);
        }
        return map;
    }

    private RouteModel createRoute(Map<String,Object> mapInfo, Map<Integer, String> coords){
        RouteModel routeModel = new RouteModel();
        routeModel.setCoords(coords);
        routeModel.setDistance((BigDecimal) mapInfo.get("distance"));
        routeModel.setDuration((BigDecimal) mapInfo.get("duration"));
        routeModel.setRouteId(99999999);
        return routeModel;
    }
}
