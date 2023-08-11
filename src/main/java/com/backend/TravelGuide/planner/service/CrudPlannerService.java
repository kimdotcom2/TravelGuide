package com.backend.TravelGuide.planner.service;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;

import java.util.List;

public interface CrudPlannerService {

    void insertPlannerFull(PlannerDTO plannerDTO);

    List<PlannerDTO> findMyPlannerByEmail(String email, int paging, int pageNum);

    List<PlannerDTO> findAllPlanner(int paging, int pageNum);

    void deletePlanner(String email, Long plannerId);

}
