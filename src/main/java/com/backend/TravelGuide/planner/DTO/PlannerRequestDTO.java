package com.backend.TravelGuide.planner.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class PlannerRequestDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerWriteRequestDTO {
        @NotBlank
        private String title;

        @NotBlank
        private LocalDate firstDate;

        @NotBlank
        private LocalDate lastDate;

        private String comment;

        @Size(min = 1)
        List<ScheduleRequestDTO> schedule;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerUpdateRequestDTO {
        @NotBlank
        private Long plannerId;
        @NotBlank
        private String title;
        @NotBlank
        private LocalDate firstDate;
        @NotBlank
        private LocalDate lastDate;

        private String comment;

        @Size(min = 1)
        List<ScheduleRequestDTO> schedule;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerSearchDTO {
        private final int size = 10;
        private int page = 1;

        private String type;    // 제목, 작성자
        private String keyword;
    }
}
