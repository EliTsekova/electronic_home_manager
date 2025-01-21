package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Apartment;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Data Access Object (DAO) for managing Apartment entities.
 * Provides methods for CRUD operations on Apartment entities in the database.
 */
public class ApartmentDao {

    /**
     * Retrieves all apartments from the database.
     *
     * @return List of all Apartment entities.
     * @throws RuntimeException if there is an issue with the database query.
     */
    public List<Apartment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Apartment", Apartment.class).list();
        }
    }

    /**
     * Finds an apartment by its unique ID.
     *
     * @param id The unique ID of the apartment to retrieve.
     * @return The Apartment entity if found.
     * @throws IllegalArgumentException if no apartment is found with the given ID.
     */
    public Apartment findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Apartment apartment = session.get(Apartment.class, id);
            if (apartment == null) {
                throw new IllegalArgumentException("Apartment not found with ID: " + id);
            }
            return apartment;
        }
    }

    /**
     * Persists a new apartment entity in the database.
     *
     * @param apartment The Apartment entity to save.
     * @throws RuntimeException if there is an issue with the transaction or database interaction.
     */
    public void save(Apartment apartment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(apartment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save apartment: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing apartment entity in the database.
     *
     * @param apartment The Apartment entity with updated values.
     * @throws RuntimeException if there is an issue with the transaction or database interaction.
     */
    public void update(Apartment apartment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(apartment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update apartment", e);
        }
    }

    /**
     * Deletes an apartment entity from the database by its ID.
     *
     * @param id The unique ID of the apartment to delete.
     * @throws IllegalArgumentException if no apartment is found with the given ID.
     * @throws RuntimeException if there is an issue with the transaction or database interaction.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Apartment apartment = session.get(Apartment.class, id);
            if (apartment == null) {
                throw new IllegalArgumentException("Apartment not found with ID: " + id);
            }
            session.remove(apartment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete apartment", e);
        }
    }
}
