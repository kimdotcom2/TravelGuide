package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannerResponseDTO {

    private Long plannerId;

    private String email;

    private String title;

    private Date firstDate;

    private Date lastDate;

    private String comment;

    List<ScheduleDTO> schedule;



}
