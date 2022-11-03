package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Resume;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ResumeRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Resume resume){
        em.persist(resume);
        return resume.getId();
    }

    public Optional<Resume> findOne(Long id){
        return Optional.ofNullable(em.find(Resume.class, id));
    }
}
