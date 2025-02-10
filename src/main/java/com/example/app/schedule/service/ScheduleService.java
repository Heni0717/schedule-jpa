package com.example.app.schedule.service;

import com.example.app.common.util.AuthUtil;
import com.example.app.schedule.dto.ScheduleResponseDto;
import com.example.app.schedule.entity.Schedule;
import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.auth.SessionStorage;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SessionStorage sessionStorage;

    public ScheduleResponseDto createSchedule(String title, String content, HttpServletRequest request) {
        String sessionId = AuthUtil.extractSessionIdFromCookies(request.getCookies());
        UserInfo userInfo = sessionStorage.getSession(sessionId);
        if (userInfo == null) {
            throw new IllegalStateException("로그인 정보 없음");
        }
        Schedule schedule = new Schedule(title, content, userInfo);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContent(), savedSchedule.getUserInfo().getUserName());
    }

    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getUserInfo().getUserName());
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, String title, String content) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        schedule.updateSchedule(title, content);
        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getUserInfo().getUserName());
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(schedule);
    }
}
