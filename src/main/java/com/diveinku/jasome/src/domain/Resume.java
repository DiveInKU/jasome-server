package com.diveinku.jasome.src.domain;

import com.diveinku.jasome.src.dto.QnaDto;
import com.diveinku.jasome.src.dto.ResumeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ResumeCategory category;

    private String title;

    // 주인X ('다'쪽이 주인) 클래스에 매핑될 필드 이름 정함
    // 연관관계가 있는 엔티티는 따로 영속화해줘야 한다.
    // 하지만 필드에 cascade를 이용하면 영속화를 쭉 내려서 전파시킬 수 있다.
    // orphanRemoval: 부모 엔티티의 컬렉션에서 자식 엔티티의 참조 제거하면 자식 엔티티가 자동으로 삭제
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeQna> resumeQnas = new ArrayList<>();

    private Resume(Member member, String title, ResumeCategory category) {
        this.member = member;
        this.title = title;
        this.category = category;
    }

    // 연관관계 매서드. 단순히 리스트에 추가만하는 게 아니라 매핑해줘야 하므로 필요
    public void addResumeQna(ResumeQna resumeQna) {
        resumeQnas.add(resumeQna);
        resumeQna.setResume(this);
    }

    // 자소서 생성 메서드
    public static Resume createResume(Member member, ResumeDto resumeDto) {
        Resume resume = new Resume(member, resumeDto.getTitle(), resumeDto.getCategory());
        for (QnaDto qna : resumeDto.getQnas()) {
            resume.addResumeQna(new ResumeQna(qna.getQuestion(), qna.getAnswer()));
        }
        return resume;
    }

    // 자소서 업데이트 메서드: 그냥 다 지우고 다시 추가한다.
    public void updateResume(ResumeDto resumeDto) {
        if (!this.title.equals(resumeDto.getTitle()))
            this.title = resumeDto.getTitle();
        if (!this.category.equals(resumeDto.getCategory()))
            this.category = resumeDto.getCategory();
        resumeQnas.clear();
        for (QnaDto qna : resumeDto.getQnas()) {
            addResumeQna(new ResumeQna(qna.getQuestion(), qna.getAnswer()));
        }
    }

}
