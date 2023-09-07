package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {

    Page<Planner> findByEmail(String email, Pageable pageable);

    Page<Planner> findAll(Pageable pageable);

    Long countByEmail(String email);

    Long countBy();

    Optional<Planner> findByPlannerId(Long id);

    void deleteByPlannerId(Long id);
}
