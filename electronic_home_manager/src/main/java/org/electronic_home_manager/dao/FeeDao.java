package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Fee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class FeeDao {

    private EntityManager entityManager;

    public FeeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Fee fee) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(fee);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Fee findById(int id) {
        return entityManager.find(Fee.class, id);
    }

    public List<Fee> findAll() {
        return entityManager.createQuery("SELECT f FROM Fee f", Fee.class).getResultList();
    }

    public List<Fee> findByBuilding(int buildingId) {
        return entityManager.createQuery(
                        "SELECT f FROM Fee f WHERE f.building.id = :buildingId", Fee.class)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }

    public void update(Fee fee) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(fee);
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
            Fee fee = entityManager.find(Fee.class, id);
            if (fee != null) {
                entityManager.remove(fee);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

}
