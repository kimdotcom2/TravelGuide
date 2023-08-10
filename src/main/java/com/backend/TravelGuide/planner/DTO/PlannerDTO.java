package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDTO {

    private Long plannerId;

    private String email;

    private String title;

    private Date firstDate;

    private Date lastDate;

    private String comment;

    List<ScheduleRequestDTO> schedule;
}
