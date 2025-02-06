package com.example.app.schedule.dto;

import com.example.app.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String content;


    // 이놈이 뭐하는놈인지 곤부하고 왜썻는지 설명하라
    // 전체조회할때 map에 넣는 용도이네요
    // 어쨋든 뭔지 몰라서 이해 해야 함
    public static ScheduleResponseDto toDto(Schedule schedule){
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent());
    }

}
