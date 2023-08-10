package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
