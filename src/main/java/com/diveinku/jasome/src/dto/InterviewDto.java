package com.diveinku.jasome.src.dto;

import com.diveinku.jasome.src.domain.InterviewQna;
import com.diveinku.jasome.src.domain.WordCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class InterviewDto {
    private String title;
    private String videoUrl;
    private List<String> emotions;
    private List<Integer> emotionValues;
    private List<Float> x;
    private List<Float> y;
    private List<InterviewQnaDto> qnas = new ArrayList<>();
    private List<WordCountDto> wordCounts = new ArrayList<>();

    @Builder
    public InterviewDto(String title, String videoUrl, List<String> emotions, List<Integer> emotionValues, List<Float> x, List<Float> y, List<InterviewQnaDto> qnas, List<WordCountDto> wordCounts) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.emotions = emotions;
        this.emotionValues = emotionValues;
        this.x = x;
        this.y = y;
        this.qnas = qnas;
        this.wordCounts = wordCounts;
    }
}
