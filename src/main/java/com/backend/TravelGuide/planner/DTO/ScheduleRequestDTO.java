package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

    @NotBlank
    private Long contentId;

    @NotBlank
    private Long contentType;

    @NotBlank
    private String address;

    @NotBlank
    private String place;

    @NotBlank
    private Long mapX;

    @NotBlank
    private Long mapY;

    @NotBlank
    private Date date;

    private LocalDateTime arriveTime;

    private LocalTime viaTime;

    private LocalDateTime startTime;

    private String thumbnailLocation;

}
