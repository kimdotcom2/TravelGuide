package com.backend.TravelGuide.planner.controller;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.service.CrudPlannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
            @RequestBody PlannerRequestDTO.PlannerWriteRequestDTO plannerRequestDTO,
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


    @GetMapping(value = "/planner/view/my_planner")
    public PlannerResponseDTO.PlannerResponseFullDTO viewMyPlanner(
            Authentication authentication,
            @RequestParam int paging,
            @RequestParam int pageNum
    ){
        List<PlannerDTO> plannerDTOList = crudPlannerService.findMyPlannerByEmail(authentication.getName(), paging-1, pageNum-1);

        List<PlannerResponseDTO.PlannerResponse> plannerResponseList = new ArrayList<>();

        for (int i = 0; i < plannerDTOList.size(); i++) {
            plannerResponseList.add(plannerMapper.plannerDTOToResponse(plannerDTOList.get(i)));
        }

        PlannerResponseDTO.PlannerResponseFullDTO plannerResponseFullDTO = new PlannerResponseDTO.PlannerResponseFullDTO();

        plannerResponseFullDTO.setPlannerResponseDTOList(plannerResponseList);

        return plannerResponseFullDTO;

    }

    @GetMapping(value = "/planner/view/all_planner")
    public PlannerResponseDTO.PlannerResponseFullDTO viewAllPlanner(
            Authentication authentication,
            @RequestParam int paging,
            @RequestParam int pageNum
    ){
        List<PlannerDTO> plannerDTOList = crudPlannerService.findAllPlanner(authentication.getName(), paging-1, pageNum-1);

        List<PlannerResponseDTO.PlannerResponse> plannerResponseList = new ArrayList<>();

        for (int i = 0; i < plannerDTOList.size(); i++) {
            plannerResponseList.add(plannerMapper.plannerDTOToResponse(plannerDTOList.get(i)));
        }

        PlannerResponseDTO.PlannerResponseFullDTO plannerResponseFullDTO = new PlannerResponseDTO.PlannerResponseFullDTO();

        plannerResponseFullDTO.setPlannerResponseDTOList(plannerResponseList);

        return plannerResponseFullDTO;

    }


    @PutMapping("/planner/edit")
    public ResponseEntity editPlanner(
            @RequestBody PlannerRequestDTO.PlannerUpdateRequestDTO plannerRequestDTO,
            Authentication authentication
    ) {

        String email = authentication.getName();
        log.info("email : " + email);

        PlannerDTO plannerDTO = plannerMapper.updateRequestToPlannerDTO(plannerRequestDTO);
        log.info(plannerDTO.toString() + " is new planner");

        plannerDTO.setEmail(email);

        crudPlannerService.updatePlannerFull(email, plannerDTO);

        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/planner/delete")
    public ResponseEntity deletePlanner(
            Authentication authentication,
            @RequestParam Long plannerId
    ) {
        String email = authentication.getName();

        log.info("email : " + email + ", planner id : " + plannerId);

        crudPlannerService.deletePlanner(email, plannerId);

        return ResponseEntity.ok().build();
    }


}
