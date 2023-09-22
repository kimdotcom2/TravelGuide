package com.backend.TravelGuide.tourapi.service.serviceImpl;

import com.backend.TravelGuide.tourapi.DTO.TourAPIDTO;
import com.backend.TravelGuide.tourapi.service.TourAPIService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourAPIServiceImpl implements TourAPIService {
    @Override
    public List<TourAPIDTO> keywordSearchApi(String serviceKey, String keyword, String pageNo) {

        String baseUrl = "apis.data.go.kr";

        WebClient webClient = WebClient.builder()
                //.uriBuilderFactory(uriBuilderFactory)
                .baseUrl(baseUrl)
                .build();

        //URL 생성
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(baseUrl)
                .path("/B551011/KorService1/searchKeyword1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", pageNo)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("keyword", keyword)
                .encode()
                .build().toUri();


        String responsexml = webClient.get()
                .uri(uri)
                .accept(MediaType.asMediaType(MediaType.APPLICATION_XML))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);})
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);})
                .bodyToMono(String.class)
                .block();

        //XML데이터를 JSON으로 변환
        JSONObject jsonObject = XML.toJSONObject(responsexml);

        JSONObject jsonObjectResponse = null;

        try {
            jsonObjectResponse = jsonObject.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

        //JSON에서 장소 배열을 추출
        //JSONObject jsonObjectBody = jsonObjectResponse.getJSONObject("body");
        //JSONObject jsonObjectItems = jsonObjectBody.getJSONObject("items");
        JSONArray jsonArray = jsonObjectResponse
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        List<TourAPIDTO> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            TourAPIDTO tourAPIDTO = new TourAPIDTO(
                    (String) temp.get("addr1"),
                    (String) temp.get("addr2"),
                    String.valueOf(temp.get("areacode")),
                    String.valueOf(temp.get("contentid")),
                    String.valueOf(temp.get("contenttypeid")),
                    String.valueOf(temp.get("mapx")),
                    String.valueOf(temp.get("mapy")),
                    (String) temp.get("title"),
                    String.valueOf(temp.get("firstimage"))
            );

            list.add(tourAPIDTO);
        }

        return list;
    }

    @Override
    public List<TourAPIDTO> areaBasedSearchApi(String serviceKey, String areaCode, String pageNo) {

        if (!areaCode.equals("1") && !areaCode.equals("2") && !areaCode.equals("31"))
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);

        String baseUrl = "apis.data.go.kr";

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        //URL 생성
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(baseUrl)
                .path("/B551011/KorService1/areaBasedList1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", pageNo)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("areaCode", areaCode)
                .encode()
                .build().toUri();

        String responsexml = webClient.get()
                .uri(uri)
                .accept(MediaType.asMediaType(MediaType.APPLICATION_XML))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);})
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);})
                .bodyToMono(String.class)
                .block();

        //XML데이터를 JSON으로 변환
        JSONObject jsonObject = XML.toJSONObject(responsexml);

        JSONObject jsonObjectResponse = null;

        //관광정보 API 접속 에러 시 예외처리
        try {
            jsonObjectResponse = jsonObject.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

        List<TourAPIDTO> list = new ArrayList<>();

        //JSON에서 장소 배열을 추출
        JSONArray jsonArray = jsonObjectResponse
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            TourAPIDTO tourAPIDTO = new TourAPIDTO(
                    (String) temp.get("addr1"),
                    (String) temp.get("addr2"),
                    String.valueOf(temp.get("areacode")),
                    String.valueOf(temp.get("contentid")),
                    String.valueOf(temp.get("contenttypeid")),
                    String.valueOf(temp.get("mapx")),
                    String.valueOf(temp.get("mapy")),
                    (String) temp.get("title"),
                    String.valueOf(temp.get("firstimage"))
            );

            list.add(tourAPIDTO);
        }


        return list;
    }
}
