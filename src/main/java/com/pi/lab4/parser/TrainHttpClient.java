package com.pi.lab4.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Yuliia Vovk
 * 12.12.15
 */
@Component
public class TrainHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    public String getStationId(String stationName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", getCookies());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://www.pz.gov.ua/rezervGR/aj_stations.php")
                .queryParam("stan", stationName);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        HashMap<String, Object>[]  listStations = (HashMap<String, Object>[]) new ObjectMapper().readValue(responseBody,
                HashMap[].class);
        String id = "";
        if (listStations != null && listStations.length > 0) {
            Map station = (Map) listStations[0];
            id = String.valueOf(station.get("nom"));
        }
        return id;
    }

    public HashMap<String, Object> getTripInfo(String depStationId, String arrStationId, String date) throws IOException {
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.put("kstotpr", Collections.singletonList(depStationId));
        postParams.put("kstprib", Collections.singletonList(arrStationId));
        postParams.put("sdate", Collections.singletonList(date));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", getCookies());

        HttpEntity<?> entity = new HttpEntity<>(postParams, headers);

        HttpEntity<String> response = restTemplate.exchange("http://www.pz.gov.ua/rezervGR/aj_g60.php",
                HttpMethod.POST, entity, String.class);

        String responseBody = response.getBody();
        HashMap<String, Object> hashMaps = (HashMap<String, Object>) new ObjectMapper().readValue(responseBody, HashMap.class);
        return  hashMaps;
    }

    public String getCookies() {
        HttpEntity entity = restTemplate.getForEntity("http://www.pz.gov.ua/rezervGR/?lid=1&mid=100240", String.class);
        return entity.getHeaders().get("Set-Cookie").get(0);
    }

}
