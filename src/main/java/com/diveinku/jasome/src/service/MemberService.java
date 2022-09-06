package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import com.diveinku.jasome.src.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void assertEmailNotExists(String email) {
        if (memberRepository.findExistsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
