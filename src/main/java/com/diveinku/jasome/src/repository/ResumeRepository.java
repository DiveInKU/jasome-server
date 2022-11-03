package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.domain.Resume;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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

    public List<Resume> findAllByMemberId(Member member) {
        return em.createQuery("select r from Resume r where r.member = :member", Resume.class)
                .setParameter("member", member)
                .getResultList();
    }

}
