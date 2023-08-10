package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    List<Planner> findByEmail(String email);

    List<Planner> findAll();

    

}
