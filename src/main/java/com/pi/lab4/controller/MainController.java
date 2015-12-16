package com.pi.lab4.controller;

import com.pi.lab4.parser.ShopHttpClient;
import com.pi.lab4.parser.TrainHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

/**
 * Yuliia Vovk
 * 12.12.15
 */
@Controller
public class MainController {

    @Autowired
    private TrainHttpClient trainHttpClient;

    @Autowired
    private ShopHttpClient shopHttpClient;

    @ResponseBody
    @RequestMapping(value = "/trip", method = RequestMethod.GET, params = {"dep_station", "arr_station", "date"})
    public ResponseEntity getDepartureStation(@RequestParam("dep_station") String depStation,
                                    @RequestParam("arr_station") String arrStation,
                                    @RequestParam("date")  String date) throws IOException {
        String depStationId = trainHttpClient.getStationId(depStation);
        String arrStationId = trainHttpClient.getStationId(arrStation);
        HashMap<String, Object> trips = trainHttpClient.getTripInfo(depStationId, arrStationId, date);
        if(trips.containsKey("error")) {
            return new ResponseEntity<>(trips.get("error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(trips.get("trains"), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/shop", method = RequestMethod.GET, params = {"city", "query"})
    public ResponseEntity getShopInfo(@RequestParam("city") String city,
                                               @RequestParam("query") String query) throws IOException {
        String cityId = shopHttpClient.getCityId(city);
        HashMap<String, Object> shopInfo = shopHttpClient.getShopInfo(cityId, query);
        if(shopInfo.containsKey("error")) {
            return new ResponseEntity<>(shopInfo.get("error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(shopInfo.get("offer"), HttpStatus.OK);
    }

    @RequestMapping(value = "/trips")
    public String getTrip() {
        return "trip";
    }

    @RequestMapping(value = "/olx")
    public String getShop() {
        return "shop";
    }

    @RequestMapping(value = "/")
    public String redirect() {
        return "redirect:/trips";
    }

}
