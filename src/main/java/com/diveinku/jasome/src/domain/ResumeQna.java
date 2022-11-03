package com.diveinku.jasome.src.domain;

import com.diveinku.jasome.src.domain.Resume;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "resume_qna")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeQna {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_qna")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String question;
    private String answer;

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public ResumeQna(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
