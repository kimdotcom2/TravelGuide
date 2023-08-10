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

    private Long contentId;

    private Long contentType;

    private String address;

    private String place;

    private Long mapX;

    private Long mapY;

    private Date date;

    private LocalDateTime arriveTime;

    private LocalTime viaTime;

    private LocalDateTime startTime;

    private String thumbnailLocation;

}
