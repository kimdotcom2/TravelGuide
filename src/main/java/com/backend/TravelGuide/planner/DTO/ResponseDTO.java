package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class ResponseDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class PlannerResponseFullDTO {
        List<PlannerResponseDTO> plannerResponseDTOList = new ArrayList<>();

        int count = 0;

        int currentPage = 0;
    }



}
