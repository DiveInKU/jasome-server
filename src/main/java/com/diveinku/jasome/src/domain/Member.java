package com.diveinku.jasome.src.domain;

import com.diveinku.jasome.src.dto.InterviewQuestionDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
// @Where(clause = "deleted = false")
// @SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewQuestion> interviewQuestions = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    private void clearInterviewQuestions(){
        interviewQuestions.clear();
    }

    private void addInterviewQuestion(InterviewQuestion interviewQuestion){
        interviewQuestions.add(interviewQuestion);
    }

    public void updateInterviewQuestion(List<InterviewQuestionDto> interviewQuestionDtos){
        clearInterviewQuestions();
        for(InterviewQuestionDto interviewQuestionDto: interviewQuestionDtos){
            addInterviewQuestion(new InterviewQuestion(this, interviewQuestionDto.getContent()));
        }
    }
}
