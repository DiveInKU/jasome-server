package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import com.diveinku.jasome.src.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // public boolean findEmailExists(String email){
    //     return memberRepository.findByEmail(email)
    //             .isPresent();
    // }

    public Long join(Member member){
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
}
