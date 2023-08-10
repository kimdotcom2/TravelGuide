package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

        private Date firstDate;

        private Date lastDate;

        private String comment;

        List<ScheduleDTO> schedule;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerResponseFullDTO {
        List<PlannerResponseDTO> plannerResponseDTOList = new ArrayList<>();

        int count = 0;

        int currentPage = 0;
    }


}
