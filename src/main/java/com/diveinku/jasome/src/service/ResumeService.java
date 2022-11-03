package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.domain.Resume;
import com.diveinku.jasome.src.domain.ResumeQna;
import com.diveinku.jasome.src.dto.QnaDto;
import com.diveinku.jasome.src.dto.ResumeDto;
import com.diveinku.jasome.src.exception.member.NonExistentMemberException;
import com.diveinku.jasome.src.repository.MemberRepository;
import com.diveinku.jasome.src.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ResumeService {
    private ResumeRepository resumeRepository;
    private MemberRepository memberRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, MemberRepository memberRepository) {
        this.resumeRepository = resumeRepository;
        this.memberRepository = memberRepository;
    }

    public Long createMembersResume(Long memberId, String title, List<QnaDto> qnas){
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        Resume resume = Resume.createResume(member, title, qnas);
        resumeRepository.save(resume);
        return resume.getId();
    }

    public ResumeDto getResumeById(Long resumeId) {
        Resume resume = resumeRepository.findOne(resumeId);
        List<QnaDto> qnas = new ArrayList<>();
        for(ResumeQna originalQna : resume.getResumeQnas()){
            qnas.add(new QnaDto(originalQna.getQuestion(), originalQna.getAnswer()));
        }
        return new ResumeDto(resume.getTitle(), qnas);
    }
}
