package com.github.gustavolara.ifmt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TalentoService {

    @Inject
    private EntityManager entityManager;

    public Optional<Talento> findById(Integer id) {
        return Optional
                .ofNullable(entityManager.find(Talento.class, id));
    }

    public List<Talento> findAll(int pageCount, int page) {
        return entityManager.createNamedQuery("Talento.findAll")
                .setFirstResult(page == 1 ? 0 : (pageCount * (page - 1)))
                .setMaxResults(pageCount)
                .getResultList();
    }

    public List<Talento> findByHabilidade(String habilidade, int pageCount, int page) {
        return entityManager.createNamedQuery("Talento.findByHabilidade")
                .setParameter("habilidade", "%" + habilidade + "%")
                .setFirstResult(page == 1 ? 0 : (pageCount * (page - 1)))
                .setMaxResults(pageCount)
                .getResultList();
    } 

    @Transactional
    public Talento saveTalento(@Valid Talento talento) {
        if (talento.getId() == null) {
            entityManager.persist(talento);
        } else {
            talento = entityManager.merge(talento);
        }
        return talento;
    }

    @Transactional
    public void deleteTalento(Integer id) {
        findById(id)
                .ifPresentOrElse(t -> entityManager.remove(t),
                        () -> {
                            throw new NotFoundException();
                        });
    }

}
