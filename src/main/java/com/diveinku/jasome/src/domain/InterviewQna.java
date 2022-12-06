package com.diveinku.jasome.src.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "interview_qna")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewQna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_qna_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;
    @Column(length = 500)
    private String question;
    @Column(length = 500)
    private String answer;

    public InterviewQna(Interview interview, String question, String answer) {
        this.interview = interview;
        this.question = question;
        this.answer = answer;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
