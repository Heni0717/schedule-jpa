package com.example.app.schedule.controller;

import com.example.app.schedule.dto.ScheduleRequestDto;
import com.example.app.schedule.dto.ScheduleResponseDto;
import com.example.app.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto, HttpServletRequest request){
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                request
        );
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> findAllSchedules(Pageable pageable){
        Page<ScheduleResponseDto> allSchedules = scheduleService.findAllSchedules(pageable);
        return new ResponseEntity<>(allSchedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){
        ScheduleResponseDto schedule = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(
            @PathVariable Long id, @RequestBody ScheduleRequestDto requestDto
    ){
        ScheduleResponseDto updatedSchedule = scheduleService.updateScheduleById(id, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
