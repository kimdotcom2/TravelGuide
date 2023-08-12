package com.backend.TravelGuide.planner.service.serviceImpl;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.Role;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.planner.domain.Schedule;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.mapper.ScheduleMapper;
import com.backend.TravelGuide.planner.repository.PlannerRepository;
import com.backend.TravelGuide.planner.repository.ScheduleRepository;
import com.backend.TravelGuide.planner.service.CrudPlannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CrudPlannerServiceImpl implements CrudPlannerService {

    private final PlannerMapper plannerMapper;

    private final ScheduleMapper scheduleMapper;

    private final PlannerRepository plannerRepository;

    private final ScheduleRepository scheduleRepository;

    private final MemberRepository memberRepository;

    public CrudPlannerServiceImpl(PlannerMapper plannerMapper, ScheduleMapper scheduleMapper, PlannerRepository plannerRepository, ScheduleRepository scheduleRepository, MemberRepository memberRepository) {
        this.plannerMapper = plannerMapper;
        this.scheduleMapper = scheduleMapper;
        this.plannerRepository = plannerRepository;
        this.scheduleRepository = scheduleRepository;
        this.memberRepository = memberRepository;
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

    @Transactional
    @Override
    public List<PlannerDTO> findMyPlannerByEmail(String email, int paging, int pageNum) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (!member.isPresent()) {

            log.info("No such user!");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }

        Pageable pageable = PageRequest.of(pageNum, paging);

        Page<Planner> plannerList = plannerRepository.findByEmail(email, pageable);
        List<PlannerDTO> plannerDTOList = new ArrayList<>();

        for (int i = 0; i < plannerList.getSize(); i++) {

            PlannerDTO plannerDTOTemp = plannerMapper.entityToPlannerDTO(plannerList.toList().get(i));

            log.info("planner id : " + plannerDTOTemp.getPlannerId().toString());

            List<Schedule> scheduleList = scheduleRepository.findByPlannerId(plannerDTOTemp.getPlannerId());

            List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

            scheduleList.stream().forEach(s -> {
                scheduleDTOList.add(scheduleMapper.entityToScheduleDTO(s));
            });

            plannerDTOTemp.setScheduleDTO(scheduleDTOList);

            plannerDTOList.add(plannerDTOTemp);

        }

        return plannerDTOList;
    }

    @Transactional
    @Override
    public List<PlannerDTO> findAllPlanner(String email, int paging, int pageNum) {

        Optional<Member> member = memberRepository.findByEmail(email);

        boolean isAdmin = false;

        log.info("check if admin!");

        if (member.isPresent() && member.get().getRole().equals(Role.ADMIN)) {
            log.info(member.get().getEmail().toString() + " with " + member.get().getRole().toString() + " is admin!");
            isAdmin = true;
        }
        else if (!member.isPresent()) {

            log.info("No such user!");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }

        Pageable pageable = PageRequest.of(pageNum, paging);

        Page<Planner> plannerList = plannerRepository.findAll(pageable);
        List<PlannerDTO> plannerDTOList = new ArrayList<>();

        for (int i = 0; i < plannerList.getSize(); i++) {

            PlannerDTO plannerDTOTemp = plannerMapper.entityToPlannerDTO(plannerList.toList().get(i));

            log.info("planner id : " + plannerDTOTemp.getPlannerId().toString());

            List<Schedule> scheduleList = scheduleRepository.findByPlannerId(plannerDTOTemp.getPlannerId());

            List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

            scheduleList.stream().forEach(s -> {
                scheduleDTOList.add(scheduleMapper.entityToScheduleDTO(s));
            });

            plannerDTOTemp.setScheduleDTO(scheduleDTOList);

            plannerDTOList.add(plannerDTOTemp);

        }

        return plannerDTOList;
    }

    @Transactional
    @Override
    public void deletePlanner(String email, Long plannerId) {

        Optional<Member> member = memberRepository.findByEmail(email);

        boolean isAdmin = false;

        if (member.isPresent() && member.get().getRole() == Role.ADMIN) {
            isAdmin = true;
        }
        else if (!member.isPresent()) {

            log.info("No such user!");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }

        Optional<Planner> planner = plannerRepository.findByPlannerId(plannerId);

        //log.info(planner.get().getPlannerId() + " is planner id");

        if (planner.isPresent() && planner.get().getEmail().equals(email)) {

            //scheduleRepository.deleteByPlannerId(planner.get().getPlannerId());
            plannerRepository.deleteByPlannerId(planner.get().getPlannerId());

            log.info("<< delete id : " + planner.get().getPlannerId() + " planner >>");

        }
        else if (planner.isPresent() && isAdmin == true && !planner.get().getEmail().equals(email)) {
            //scheduleRepository.deleteByPlannerId(planner.get().getPlannerId());
            plannerRepository.deleteByPlannerId(planner.get().getPlannerId());

            log.info("<< delete id : " + planner.get().getPlannerId() + " planner by Admin>>");
        }
        else if (planner.isPresent() && !planner.get().getEmail().equals(email)) {
            log.info("Invalid delete request");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }
        else if (!planner.isPresent()) {
            log.info("No such an planner");

            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    @Transactional
    public void updatePlannerFull(String email, PlannerDTO plannerDTO) {

        Optional<Member> member = memberRepository.findByEmail(email);

        boolean isAdmin = false;

        if (member.isPresent() && member.get().getRole() == Role.ADMIN) {
            isAdmin = true;
        }
        else if (!member.isPresent()) {

            log.info("No such user!");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }

        Optional<Planner> planner = plannerRepository.findByPlannerId(plannerDTO.getPlannerId());

        if (planner.isPresent() && planner.get().getEmail().equals(email)) {

            scheduleRepository.deleteByPlannerId(planner.get().getPlannerId());
            insertPlannerFull(plannerDTO);

            log.info("<< update id : " + planner.get().getPlannerId() + " planner >>");

        }
        else if (planner.isPresent() && isAdmin == true && !planner.get().getEmail().equals(email)) {
            scheduleRepository.deleteByPlannerId(planner.get().getPlannerId());
            insertPlannerFull(plannerDTO);

            log.info("<< update id : " + planner.get().getPlannerId() + " planner by Admin>>");
        }
        else if (planner.isPresent() && !planner.get().getEmail().equals(email) && isAdmin == false) {
            log.info("Invalid delete request");

            throw new HttpServerErrorException(HttpStatus.FORBIDDEN);
        }
        else if (!planner.isPresent()) {
            log.info("No such an planner");

            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

    }
}
