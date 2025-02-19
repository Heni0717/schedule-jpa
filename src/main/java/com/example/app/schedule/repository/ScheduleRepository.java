package com.example.app.schedule.repository;

import com.example.app.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    default Schedule findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정")
        );
    }

    void deleteByUserInfoId(Long id);
}
