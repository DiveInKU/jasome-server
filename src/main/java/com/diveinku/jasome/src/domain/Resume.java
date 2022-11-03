package com.diveinku.jasome.src.domain;

import com.diveinku.jasome.src.dto.QnaDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
Order와 OrderItem처럼
Resume와 ResumeQna가 있다.
*/

@Entity
@Table(name = "resume")
@Getter
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    // 주인X ('다'쪽이 주인) 클래스에 매핑될 필드 이름 정함
    // 연관관계가 있는 엔티티는 따로 영속화해줘야 한다.
    // 하지만 필드에 cascade를 이용하면 영속화를 쭉 내려서 전파시킬 수 있다.
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ResumeQna> resumeQnas = new ArrayList<>();

    // 연관관계 매서드. 단순히 리스트에 추가만하는 게 아니라 매핑해줘야 하므로 필요
    public void addResumeQna(ResumeQna resumeQna) {
        resumeQnas.add(resumeQna);
        resumeQna.setResume(this);
    }

    // 자소서 생성 메서드
    public static Resume createResume(Member member, String title, List<QnaDto> qnas) {
        Resume resume = new Resume();
        resume.setMember(member);
        resume.setTitle(title);
        for (QnaDto qna : qnas) {
            resume.addResumeQna(new ResumeQna(qna.getQuestion(), qna.getAnswer()));
        }
        return resume;
    }

    // 자소서 업데이트 메서드: 그냥 다 지우고 다시 추가한다.
    public void updateResume(String title, List<QnaDto> qnas){
        this.title = title;
        resumeQnas.clear();
        for (QnaDto qna : qnas) {
            addResumeQna(new ResumeQna(qna.getQuestion(), qna.getQuestion()));
        }
    }


    public void setMember(Member member) {
        this.member = member;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
