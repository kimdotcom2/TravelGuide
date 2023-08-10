package com.backend.TravelGuide.planner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Planner")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Planner extends BaseEntity{

    @Column(name = "planner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long plannerId;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "first_date")
    private Date firstDate;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "comment")
    @Nullable
    private String comment;

    @Version
    private Long version;

    @OneToMany
    @JoinColumn(name = "planner_id")
    private List<Schedule> scheduleList = new ArrayList<>();


}
