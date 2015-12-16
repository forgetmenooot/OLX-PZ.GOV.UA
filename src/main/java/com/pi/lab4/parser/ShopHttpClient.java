package com.pi.lab4.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by oleg
 * Date: 13.12.15
 * Time: 19:38
 */
@Component
public class ShopHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    public String getCityId(String cityName) throws IOException {
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.put("data", Collections.singletonList(cityName));
        postParams.put("withRegions", Collections.singletonList("0"));


        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", getCookies());

        HttpEntity<?> entity = new HttpEntity<>(postParams, headers);

        HttpEntity<String> response = restTemplate.exchange("http://olx.ua/ajax/geo6/autosuggest/",
                HttpMethod.POST, entity, String.class);

        String responseBody = response.getBody();
        HashMap<String, Object> cityData = (HashMap<String, Object>) new ObjectMapper().readValue(responseBody, HashMap.class);
        String id = "";
        if (cityData != null && !cityData.isEmpty()) {
            List cities = (List) cityData.get("data");
            // get first best match
            if (cities != null && cities.size() > 0)
                id = String.valueOf(((Map) cities.get(0)).get("id"));
        }
        return id;
    }

    public HashMap<String, Object> getShopInfo(String cityId, String good) throws IOException {
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.put("search[city_id]", Collections.singletonList(cityId));
        postParams.put("q", Collections.singletonList(good));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", getCookies());

        HttpEntity<?> entity = new HttpEntity<>(postParams, headers);

        HttpEntity<String> response = restTemplate.exchange("http://olx.ua/list/",
                HttpMethod.POST, entity, String.class);
        String resultPage = response.getHeaders().get("location").get(0);

        entity = new HttpEntity<>(headers);
        HttpEntity<String> page = restTemplate.exchange(resultPage,
                HttpMethod.GET, entity, String.class);
        String pageBody = page.getBody();
        Document doc = Jsoup.parse(pageBody);

        HashMap<String, Object> result = new HashMap<>();
        Elements empty = doc.select("div.emptynew p");
        if (empty != null &&
                empty.size() > 0 &&
                empty.first().text().contains("Не найдено ни одного")) {
            result.put("error", "No data for current search found!");
            return result;
        }

        Elements offers = doc.select("td.offer");

        List<Object> parsedOffers = new LinkedList<>();

        for (Element e : offers) {
            Map<String, String> offer = new LinkedHashMap<>();
            Element name = e.select("h3 strong").first();
            if (name == null) {
                continue;
            }
            offer.put("name", name.text());

            Element price = e.select("p.price strong").first();
            if (price == null) {
                continue;
            }
            offer.put("price", price.text());

            Element url = e.select("h3 a.detailsLink").first();
            if (url == null) {
                continue;
            }
            offer.put("url", url.absUrl("href"));

            parsedOffers.add(offer);
        }
        result.put("offer", parsedOffers);
        return result;
    }

    public String getCookies() {
        HttpEntity entity = restTemplate.getForEntity("http://olx.ua/", String.class);
        return entity.getHeaders().get("Set-Cookie").get(0);
    }
}
