package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.ResumeQna;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ResumeQnaRepository {

    @PersistenceContext
    private EntityManager em;

    public void delete(ResumeQna resumeQna){
        em.remove(resumeQna);
    }
}
