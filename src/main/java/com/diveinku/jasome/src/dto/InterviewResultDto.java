package com.diveinku.jasome.src.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class InterviewResultDto {
    private String title;
    private List<InterviewQnaDto> qnas;
    private List<String> emotions;
    private List<Integer> emotionValues;
    private List<WordCountDto> wordCounts;
    private List<Float> x;
    private List<Float> y;
    private String videoUrl;
}
