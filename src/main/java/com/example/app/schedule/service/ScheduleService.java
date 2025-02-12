package com.example.app.schedule.service;

import com.example.app.common.config.SessionStorage;
import com.example.app.common.util.AuthUtil;
import com.example.app.schedule.dto.ScheduleResponseDto;
import com.example.app.schedule.entity.Schedule;
import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SessionStorage sessionStorage;

    @Transactional
    public ScheduleResponseDto createSchedule(String title, String content, HttpServletRequest request) {
        String sessionId = AuthUtil.extractSessionIdFromCookies(request.getCookies());
        UserInfo userInfo = sessionStorage.getSession(sessionId);
        if (userInfo == null) {
            throw new IllegalStateException("로그인 정보 없음");
        }
        Schedule schedule = new Schedule(title, content, userInfo);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> findAllSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable);
        return schedulePage.map(ScheduleResponseDto::new);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, String title, String content) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        schedule.updateSchedule(title, content);
        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void deleteSchedulesByUserInfo(Long id) {
        scheduleRepository.deleteByUserInfoId(id);
    }
}
