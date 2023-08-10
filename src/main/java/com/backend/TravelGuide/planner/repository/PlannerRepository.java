package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
