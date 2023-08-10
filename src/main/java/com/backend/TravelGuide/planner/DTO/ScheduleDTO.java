package com.backend.TravelGuide.planner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Long plannerId;

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
