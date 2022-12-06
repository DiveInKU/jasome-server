package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Interview;
import com.diveinku.jasome.src.domain.Resume;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class InterviewRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Interview interview){
        em.persist(interview);
        return interview.getId();
    }
}
