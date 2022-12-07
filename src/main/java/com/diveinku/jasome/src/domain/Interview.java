package com.diveinku.jasome.src.domain;

import com.diveinku.jasome.src.dto.InterviewResultDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "interview")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;

    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String videoUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            // name = "emotion",
            joinColumns = @JoinColumn(name = "interview_id")
    )
    private List<String> emotions;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            // name = "emotion",
            joinColumns = @JoinColumn(name = "interview_id")
    )
    private List<Integer> emotionValues;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            // name = "emotion",
            joinColumns = @JoinColumn(name = "interview_id")
    )
    private List<Float> x;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            // name = "emotion",
            joinColumns = @JoinColumn(name = "interview_id")
    )
    private List<Float> y;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewQna> qnas = new ArrayList<>();

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordCount> wordCounts = new ArrayList<>();

    @Builder
    public Interview(Member member, String title, String videoUrl, List<String> emotions, List<Integer> emotionValues, List<Float> x, List<Float> y, List<InterviewQna> qnas, List<WordCount> wordCounts) {
        this.member = member;
        this.title = title;
        this.videoUrl = videoUrl;
        this.emotions = emotions;
        this.emotionValues = emotionValues;
        this.x = x;
        this.y = y;
        this.qnas = qnas;
        this.wordCounts = wordCounts;
    }

    public static Interview createInterview(Member member, InterviewResultDto interviewResultDto) {
        Interview interview = Interview.builder()
                .title(interviewResultDto.getTitle())
                .videoUrl(interviewResultDto.getVideoUrl())
                .emotions(interviewResultDto.getEmotions())
                .emotionValues(interviewResultDto.getEmotionValues())
                .x(interviewResultDto.getX())
                .y(interviewResultDto.getY())
                .member(member)
                .build();
        interview.qnas = interviewResultDto.getQnas().stream().map(qna -> new InterviewQna(interview, qna.getQuestion(), qna.getAnswer())).collect(Collectors.toList());
        interview.wordCounts = interviewResultDto.getWordCounts().stream().map(wordCount -> new WordCount(interview, wordCount.getWord(), wordCount.getCount())).collect(Collectors.toList());
        return interview;
    }
}
