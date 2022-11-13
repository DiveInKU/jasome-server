package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.domain.Resume;
import com.diveinku.jasome.src.domain.ResumeCategory;
import com.diveinku.jasome.src.domain.ResumeQna;
import com.diveinku.jasome.src.dto.QnaDto;
import com.diveinku.jasome.src.dto.ResumeDto;
import com.diveinku.jasome.src.dto.ResumePreviewDto;
import com.diveinku.jasome.src.exception.member.NonExistentMemberException;
import com.diveinku.jasome.src.exception.resume.NonExistentResumeException;
import com.diveinku.jasome.src.repository.MemberRepository;
import com.diveinku.jasome.src.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public Long createMembersResume(Long memberId, ResumeDto resumeDto) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        Resume resume = Resume.createResume(member, resumeDto);
        resumeRepository.save(resume);
        return resume.getId();
    }

    public ResumeDto getResumeById(Long resumeId) {
        Resume resume = resumeRepository.findOne(resumeId)
                .orElseThrow(NonExistentResumeException::new);
        return translateToDto(resume);
    }

    public void updateResume(Long resumeId, ResumeDto resumeDto) {
        Resume resume = resumeRepository.findOne(resumeId)
                .orElseThrow(NonExistentResumeException::new);
        resume.updateResume(resumeDto);
    }

    public List<ResumePreviewDto> getMembersResumePreviews(long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        return resumeRepository.findAllByMemberId(member).stream().map(r -> new ResumePreviewDto(r.getId(), r.getTitle()))
                .collect(Collectors.toList());
    }

    public List<ResumeDto> getMembersResumes(long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        return resumeRepository.findAllByMemberId(member).stream().map(r -> translateToDto(r))
                .collect(Collectors.toList());
    }

    public void deleteResumeById(long resumeId) {
        Resume resume = resumeRepository.findOne(resumeId)
                .orElseThrow(NonExistentResumeException::new);
        resumeRepository.delete(resume);
    }

    private static ResumeDto translateToDto(Resume resume) {
        List<QnaDto> qnas = new ArrayList<>();
        for (ResumeQna qna : resume.getResumeQnas()) {
            qnas.add(new QnaDto(qna.getQuestion(), qna.getAnswer()));
        }
        return new ResumeDto(resume.getTitle(), resume.getResumeCategory(), qnas);
    }
}
