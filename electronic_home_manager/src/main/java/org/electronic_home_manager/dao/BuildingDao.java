package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Building;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BuildingDao {

    private EntityManager entityManager;

    public BuildingDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Building building) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(building);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Building findById(int id) {
        return entityManager.find(Building.class, id);
    }

    public List<Building> findAll() {
        return entityManager.createQuery("SELECT b FROM Building b", Building.class).getResultList();
    }

    public void update(Building building) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(building);
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
            Building building = entityManager.find(Building.class, id);
            if (building != null) {
                entityManager.remove(building);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
