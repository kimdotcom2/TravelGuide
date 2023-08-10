package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannerRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private Date firstDate;

    @NotBlank
    private Date lastDate;

    private String comment;

    @Size(min = 1)
    List<ScheduleRequestDTO> schedule;

}
