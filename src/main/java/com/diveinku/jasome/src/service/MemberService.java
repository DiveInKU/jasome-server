package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.CommonQuestion;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.InterviewQuestionDto;
import com.diveinku.jasome.src.dto.member.LoginReq;
import com.diveinku.jasome.src.dto.member.MemberProfileRes;
import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import com.diveinku.jasome.src.exception.member.IncorrectPasswordException;
import com.diveinku.jasome.src.exception.member.NonExistentEmailException;
import com.diveinku.jasome.src.exception.member.NonExistentMemberException;
import com.diveinku.jasome.src.repository.CommonQuestionRepository;
import com.diveinku.jasome.src.repository.MemberRepository;
import com.diveinku.jasome.src.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private final CommonQuestionRepository commonQuestionRepository;


    @Autowired
    public MemberService(PasswordEncoder passwordEncoder, JwtService jwtService, MemberRepository memberRepository, CommonQuestionRepository commonQuestionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
        this.commonQuestionRepository = commonQuestionRepository;
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        validateDuplicateEmail(member.getEmail());
    }

    public void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new DuplicateEmailException();
                });
    }

    public String authenticateMember(LoginReq loginReq) {
        Member member = memberRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(NonExistentEmailException::new);
        if (!passwordEncoder.matches(loginReq.getPassword(), member.getPassword()))
            throw new IncorrectPasswordException();
        return jwtService.createJwt(member.getId());
    }

    public MemberProfileRes retrieveMemberProfile(long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        return new MemberProfileRes(member.getEmail(), member.getName());
    }

    public void addQuestions(long memberId, List<InterviewQuestionDto> questions) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        member.updateInterviewQuestion(questions);
    }

    public List<InterviewQuestionDto> getQuestions(long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        return member.getInterviewQuestions()
                .stream()
                .map(question -> new InterviewQuestionDto(question.getContent())).collect(Collectors.toList());
    }

    public List<InterviewQuestionDto> getRandomQuestions(long memberId, boolean commonRandom, boolean memberRandom, int questionCount) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        int totalCount = questionCount;
        List<InterviewQuestionDto> result = new ArrayList<>();
        if (memberRandom) {
            int memberQuestionCount = totalCount;
            if (commonRandom)
                memberQuestionCount = (int) Math.ceil((double) memberQuestionCount / 2);
            List<InterviewQuestionDto> memberQuestions = getRandomMemberQuestions(member, memberQuestionCount);
            result.addAll(memberQuestions);
            totalCount -= memberQuestions.size();
        }
        if (commonRandom) {
            List<InterviewQuestionDto> commonQuestions = getRandomCommonQuestions(totalCount);
            result.addAll(commonQuestions);
            totalCount -= commonQuestions.size();
        }
        Collections.shuffle(result);
        return result;
    }

    private List<InterviewQuestionDto> getRandomMemberQuestions(Member member, int count) {
        List<InterviewQuestionDto> questions = member.getInterviewQuestions()
                .stream()
                .map(question -> new InterviewQuestionDto(question.getContent()))
                .collect(Collectors.toList());

        Collections.shuffle(questions);

        int memberQuestionCount = count;
        return questions.subList(0, Math.min(memberQuestionCount, questions.size()));
    }

    private List<InterviewQuestionDto> getRandomCommonQuestions(int count) {
        List<Long> indices = new ArrayList<>();
        for (long i = 1; i <= commonQuestionRepository.getCommonQuestionCount(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        return commonQuestionRepository.findByIds(indices.subList(0, Math.min(count, indices.size())))
                .stream()
                .map(InterviewQuestionDto::new)
                .collect(Collectors.toList());
    }
}
