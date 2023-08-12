package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDTO {

    private Long plannerId;

    private String email;

    private String title;

    private LocalDate firstDate;

    private LocalDate lastDate;

    private String comment;

    List<ScheduleRequestDTO> schedule;

    List<ScheduleDTO> scheduleDTO;
}
