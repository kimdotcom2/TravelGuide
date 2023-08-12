package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /*@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerDeleteRequestDTO {
        @NotBlank
        private String title;

        @NotBlank
        private Date firstDate;

        @NotBlank
        private Date lastDate;

        private String comment;
    }*/

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


}
