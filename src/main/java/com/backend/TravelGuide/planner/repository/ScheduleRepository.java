package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByPlannerId(long plannerId);

}
