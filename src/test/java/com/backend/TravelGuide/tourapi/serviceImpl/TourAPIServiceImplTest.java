package com.backend.TravelGuide.tourapi.serviceImpl;

import com.backend.TravelGuide.tourapi.DTO.TourAPIDTO;
import com.backend.TravelGuide.tourapi.service.TourAPIService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourAPIServiceImplTest {

    @Autowired
    TourAPIService tourAPIService = new TourAPIServiceImpl();

    String key = "gIY262VtYdeHjkVj0LjSSFlkA56X/H2B/WviOklAVEu6MHcP2YY1MO/sj4K30CfAmMCh/xeo7DCl8iyIQj3D6g==";

    @Test
    void keywordSearchApi() {
        List<TourAPIDTO> tourAPIDTO = tourAPIService.keywordSearchApi(key, "서울", "1");

        tourAPIDTO.stream().forEach(a -> {System.out.println(a.toString());});

    }

    @Test
    void areaBasedSearchApi() {

        List<TourAPIDTO> tourAPIDTO = tourAPIService.areaBasedSearchApi(key, "1", "1");

        tourAPIDTO.stream().forEach(a -> {System.out.println(a.toString());});
    }
}