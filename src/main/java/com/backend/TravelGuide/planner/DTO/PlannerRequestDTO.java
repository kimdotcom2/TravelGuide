package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannerRequestDTO {

    private String title;

    private Date firstDate;

    private Date lastDate;

    private String comment;

    List<ScheduleRequestDTO> schedule;

}
