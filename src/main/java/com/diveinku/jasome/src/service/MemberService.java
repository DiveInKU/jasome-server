package com.diveinku.jasome.src.service;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.LoginReq;
import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import com.diveinku.jasome.src.exception.member.IncorrectPasswordException;
import com.diveinku.jasome.src.exception.member.NonExistentEmailException;
import com.diveinku.jasome.src.repository.MemberRepository;
import com.diveinku.jasome.src.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;


    @Autowired
    public MemberService(PasswordEncoder passwordEncoder, JwtService jwtService, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
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
        Optional<Member> member = memberRepository.findByEmail(loginReq.getEmail());
        if (member.isEmpty())
            throw new NonExistentEmailException();
        if (!passwordEncoder.matches(loginReq.getPassword(), member.get().getPassword()))
            throw new IncorrectPasswordException();
        return jwtService.createJwt(member.get().getId());
    }
}
