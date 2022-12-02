package com.diveinku.jasome.src.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommonQuestionRepository {
    @PersistenceContext
    private EntityManager em;

    public long getCommonQuestionCount(){
        return em.createQuery("select count(q) from CommonQuestion as q", Long.class)
                .getSingleResult();
    }

    public List<String> findByIds(List<Long> ids){
        return em.createQuery("select q.content from CommonQuestion as q where q.id in :ids", String.class)
                .setParameter("ids", ids)
                .getResultList();
    }

}
