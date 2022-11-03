package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.QnaDto;
import com.diveinku.jasome.src.domain.Resume;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ResumeRepositoryTest {

    @Autowired
    ResumeRepository resumeRepository = new ResumeRepository();

    @Autowired
    MemberRepository memberRepository = new MemberRepository();

    @Test
    @Rollback(false)
    void createResume() {
        // given
        Member member = new Member("test@gmail.com", "test", "test");
        memberRepository.save(member);
        // when
        List<QnaDto> qnas = new ArrayList<>();
        qnas.add(new QnaDto("질문1", "답변1"));
        qnas.add(new QnaDto("질문2", "답변2"));
        qnas.add(new QnaDto("질문3", "답변3"));
        Resume resume = Resume.createResume(member, "2022 자기소개서", qnas);
        Long resumeId = resumeRepository.save(resume);

        // then
        Resume foundResume = resumeRepository.findOne(resumeId);
        for (int i = 0; i < qnas.size(); i++) {
            Assertions.assertThat(foundResume.getResumeQnas().get(i).getQuestion()).isEqualTo(qnas.get(i).getQuestion());
            Assertions.assertThat(foundResume.getResumeQnas().get(i).getAnswer()).isEqualTo(qnas.get(i).getAnswer());
        }
        Assertions.assertThat(foundResume).isSameAs(resume);
    }

    @Test
    @Transactional
    @Rollback(false)
    void updateResume() {
    }
}