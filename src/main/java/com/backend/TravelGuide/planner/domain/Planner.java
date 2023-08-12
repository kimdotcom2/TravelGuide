package com.backend.TravelGuide.planner.domain;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate firstDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Column(name = "comment")
    @Nullable
    private String comment;

    @Version
    private Long version;

    /*@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private List<Schedule> scheduleList = new ArrayList<>();*/
    public void updateInfo(PlannerDTO plannerDTO) {
        this.title = plannerDTO.getTitle();
        this.firstDate = plannerDTO.getFirstDate();
        this.lastDate = plannerDTO.getLastDate();
        this.comment = plannerDTO.getComment();
    }


}
