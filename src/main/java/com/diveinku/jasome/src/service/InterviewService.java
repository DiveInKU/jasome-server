package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.Interview;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.*;
import com.diveinku.jasome.src.exception.interview.NonExistentInterviewException;
import com.diveinku.jasome.src.exception.member.NonExistentMemberException;
import com.diveinku.jasome.src.exception.resume.NonExistentResumeException;
import com.diveinku.jasome.src.repository.CommonQuestionRepository;
import com.diveinku.jasome.src.repository.InterviewRepository;
import com.diveinku.jasome.src.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterviewService {
    private final MemberRepository memberRepository;
    private final CommonQuestionRepository commonQuestionRepository;
    private final InterviewRepository interviewRepository;

    @Autowired
    public InterviewService(MemberRepository memberRepository, CommonQuestionRepository commonQuestionRepository, InterviewRepository interviewRepository) {
        this.memberRepository = memberRepository;
        this.commonQuestionRepository = commonQuestionRepository;
        this.interviewRepository = interviewRepository;
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

    public long createMembersInterview(long memberId, InterviewResultDto interviewResultDto) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        Interview interview = Interview.createInterview(member, interviewResultDto);
        interviewRepository.save(interview);
        return interview.getId();
    }

    public List<InterviewPreviewDto> getMembersInterviewPreviews(long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(NonExistentMemberException::new);
        return interviewRepository.findAllByMemberId(member).stream().map(r -> new InterviewPreviewDto(r.getId(), r.getTitle()))
                .collect(Collectors.toList());
    }

    public InterviewDto getInterviewById(Long interviewId) {
        Interview interview = interviewRepository.findOne(interviewId)
                .orElseThrow(NonExistentInterviewException::new);
        return InterviewDto
                .builder()
                .title(interview.getTitle())
                .videoUrl(interview.getVideoUrl())
                .emotions(interview.getEmotions())
                .emotionValues(interview.getEmotionValues())
                .x(interview.getX())
                .y(interview.getY())
                .qnas(interview.getQnas().stream().map(qna -> new InterviewQnaDto(qna.getQuestion(), qna.getAnswer())).collect(Collectors.toList()))
                .wordCounts(interview.getWordCounts().stream().map(wordCount -> new WordCountDto(wordCount.getWord(), wordCount.getNumber())).collect(Collectors.toList()))
                .build();
    }
}
