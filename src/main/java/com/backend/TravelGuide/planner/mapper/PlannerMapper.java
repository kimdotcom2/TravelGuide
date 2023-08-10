package com.backend.TravelGuide.planner.mapper;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;
import com.backend.TravelGuide.planner.domain.Planner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PlannerMapper {

    public Planner plannerDTOToEntity(PlannerDTO plannerDTO);

    public PlannerDTO entityToPlannerDTO(Planner planner);

    public PlannerDTO requestToPlannerDTO(PlannerRequestDTO plannerRequestDTO);

    public Planner requestToEntity(PlannerRequestDTO plannerRequestDTO);

    @Mappings(
            {
                    @Mapping(target = "schedule", source = "scheduleDTO")
            }
    )
    public PlannerResponseDTO plannerDTOToResponse(PlannerDTO plannerDTO);

}
