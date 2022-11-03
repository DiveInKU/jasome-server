package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Member member = new Member("test@gmail.com", "test", "test");
        memberRepository.save(member);
        //when
        Member foundMember = memberRepository.findByEmail("test@gmail.com").get();
        //then
        Assertions.assertThat(foundMember).isSameAs(member);
    }
}