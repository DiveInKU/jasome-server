package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    boolean findExistsByEmail(String email);
}
