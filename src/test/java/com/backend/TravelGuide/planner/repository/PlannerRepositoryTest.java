package com.backend.TravelGuide.planner.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlannerRepositoryTest {
    @Autowired
    PlannerRepository plannerRepository;

    @Test
    @Transactional
    void deleteByPlannerId() {
        plannerRepository.deleteByPlannerId(2L);
    }
}