package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class CompanyDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Company company) {
        entityManager.persist(company);
    }

    public Company findById(int id) {
        return entityManager.find(Company.class, id);
    }

    public List<Company> findAll() {
        return entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
    }

    @Transactional
    public void update(Company company) {
        entityManager.merge(company);
    }

    @Transactional
    public void delete(int id) {
        Company company = findById(id);
        if (company != null) {
            entityManager.remove(company);
        }
    }
}
