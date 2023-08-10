package com.backend.TravelGuide.planner.controller;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.service.CrudPlannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "플래너&스케줄 컨트롤러")
@Slf4j
@RestController
public class PlannerController {

    private final PlannerMapper plannerMapper;
    private final CrudPlannerService crudPlannerService;

    public PlannerController(PlannerMapper plannerMapper, CrudPlannerService crudPlannerService) {
        this.plannerMapper = plannerMapper;
        this.crudPlannerService = crudPlannerService;
    }

    @PostMapping(value = "/planner/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPlanner(
            @RequestBody PlannerRequestDTO plannerRequestDTO,
            Authentication authentication
            ) {

        log.info("planner : " + plannerRequestDTO.toString());

        String email = authentication.getName();
        log.info("email : " + email);

        PlannerDTO plannerDTO = plannerMapper.requestToPlannerDTO(plannerRequestDTO);
        plannerDTO.setEmail(email);

        crudPlannerService.insertPlannerFull(plannerDTO);

        return ResponseEntity.ok().build();

    }


}
