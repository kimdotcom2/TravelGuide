package com.backend.TravelGuide.planner.domain;

import com.backend.TravelGuide.member.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Schedule extends BaseEntity {

    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long scheduleId;

    @Column(name = "planner_id")
    private Long plannerId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "content_type")
    private Long contentType;

    @Column(name = "address")
    private String address;

    @Column(name = "place")
    private String place;

    @Column(name = "mapx")
    private Long mapX;

    @Column(name = "mapy")
    private Long mapY;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "arrive_time")
    @Nullable
    private LocalDateTime arriveTime;

    @Column(name = "via_time")
    @Nullable
    private LocalTime viaTime;

    @Column(name = "start_time")
    @Nullable
    private LocalDateTime startTime;

    @Column(name = "thumbnail_location")
    private String thumbnailLocation;

    @Version
    private Long version;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;*/

}
