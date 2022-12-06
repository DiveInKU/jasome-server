package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Interview;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.domain.Resume;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class InterviewRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Interview interview){
        em.persist(interview);
        return interview.getId();
    }

    public List<Interview> findAllByMemberId(Member member) {
        return em.createQuery("select interview from Interview interview where interview.member = :member", Interview.class)
                .setParameter("member", member)
                .getResultList();
    }

    public Optional<Interview> findOne(Long interviewId) {
        return Optional.ofNullable(em.find(Interview.class, interviewId));
    }
}
