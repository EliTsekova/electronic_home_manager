package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Resident;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ResidentDao {

    private EntityManager entityManager;

    public ResidentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Resident resident) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(resident);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Resident findById(int id) {
        return entityManager.find(Resident.class, id);
    }

    public List<Resident> findAll() {
        return entityManager.createQuery("SELECT r FROM Resident r", Resident.class).getResultList();
    }

    public void update(Resident resident) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(resident);
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
            Resident resident = entityManager.find(Resident.class, id);
            if (resident != null) {
                entityManager.remove(resident);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    public List<Resident> findByApartmentId(int apartmentId) {
        return entityManager.createQuery("SELECT r FROM Resident r WHERE r.apartment.id = :apartmentId", Resident.class)
                .setParameter("apartmentId", apartmentId)
                .getResultList();
    }
}
