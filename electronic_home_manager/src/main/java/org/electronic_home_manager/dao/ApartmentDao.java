package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Apartment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ApartmentDao {
    private final EntityManager entityManager;

    public ApartmentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Apartment> findAll() {
        return entityManager.createQuery("SELECT a FROM Apartment a", Apartment.class).getResultList();
    }

    public Apartment findById(int id) {
        return entityManager.find(Apartment.class, id);
    }

    public void save(Apartment apartment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(apartment);
        transaction.commit();
    }

    public void update(Apartment apartment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(apartment);
        transaction.commit();
    }

    public void delete(int id) {
        Apartment apartment = findById(id);
        if (apartment != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(apartment);
            transaction.commit();
        }
    }
}
