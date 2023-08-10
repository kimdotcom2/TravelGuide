package com.backend.TravelGuide.planner.mapper;

import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleRequestDTO;
import com.backend.TravelGuide.planner.domain.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    public Schedule scheduleDTOToEntity(ScheduleDTO scheduleDTO);

    public ScheduleDTO entityToScheduleDTO(Schedule schedule);

    public ScheduleDTO requestToScheduleDTO(ScheduleRequestDTO scheduleRequestDTO);

    public Schedule requestToEntity(ScheduleRequestDTO scheduleRequestDTO);
}
