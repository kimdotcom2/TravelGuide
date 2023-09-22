package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlannerResponseDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerResponse {

        private Long plannerId;

        private String email;

        private String title;

        private LocalDate firstDate;

        private LocalDate lastDate;

        private String comment;

        List<ScheduleDTO> schedule;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerResponseFullDTO {
        List<PlannerResponseDTO.PlannerResponse> plannerResponseDTOList = new ArrayList<>();

        int count = 0;

        int currentPage = 0;
    }


}
