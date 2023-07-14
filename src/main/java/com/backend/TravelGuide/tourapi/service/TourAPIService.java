package com.backend.TravelGuide.tourapi.service;

import java.util.List;
import com.backend.TravelGuide.tourapi.DTO.TourAPIDTO;

public interface TourAPIService {

    //키워드기반검색 API
    public List<TourAPIDTO> keywordSearchApi(String serviceKey, String keyword, String pageNo);

    //지역기반검색 API
    public List<TourAPIDTO> areaBasedSearchApi(String serviceKey, String areaCode, String pageNo);

}
