package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Contract;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ContractDao {

    private EntityManager entityManager;

    public ContractDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Contract contract) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(contract);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Contract findById(int id) {
        return entityManager.find(Contract.class, id);
    }

    public List<Contract> findAll() {
        return entityManager.createQuery("SELECT c FROM Contract c", Contract.class).getResultList();
    }

    public void update(Contract contract) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(contract);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public void delete(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Contract contract = entityManager.find(Contract.class, id);
            if (contract != null) {
                entityManager.remove(contract);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
