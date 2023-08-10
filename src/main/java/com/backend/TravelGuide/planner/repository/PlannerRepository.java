package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    Page<Planner> findByEmail(String email, Pageable pageable);

    Page<Planner> findAll(Pageable pageable);

    Long countByEmail(String email);

    Long countBy();

}
