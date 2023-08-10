package com.backend.TravelGuide.planner.service.serviceImpl;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.mapper.ScheduleMapper;
import com.backend.TravelGuide.planner.repository.PlannerRepository;
import com.backend.TravelGuide.planner.repository.ScheduleRepository;
import com.backend.TravelGuide.planner.service.CrudPlannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class CrudPlannerServiceImpl implements CrudPlannerService {

    private final PlannerMapper plannerMapper;

    private final ScheduleMapper scheduleMapper;

    private final PlannerRepository plannerRepository;

    private final ScheduleRepository scheduleRepository;

    public CrudPlannerServiceImpl(PlannerMapper plannerMapper, ScheduleMapper scheduleMapper, PlannerRepository plannerRepository, ScheduleRepository scheduleRepository) {
        this.plannerMapper = plannerMapper;
        this.scheduleMapper = scheduleMapper;
        this.plannerRepository = plannerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public void insertPlannerFull(PlannerDTO plannerDTO) {

        Planner planner = plannerMapper.plannerDTOToEntity(plannerDTO);

        Planner plannerResult = plannerRepository.save(planner);

        plannerDTO.getSchedule().stream().forEach(s -> {
            ScheduleDTO scheduleDTO = scheduleMapper.requestToScheduleDTO(s);
            scheduleDTO.setPlannerId(plannerResult.getPlannerId());
            scheduleRepository.save(scheduleMapper.scheduleDTOToEntity(scheduleDTO));
                }
        );

        log.info("<< add " + plannerDTO.getTitle() + " to table >>");

    }
}
