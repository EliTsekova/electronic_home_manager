package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Resident;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Data Access Object (DAO) for managing Resident entities.
 * Provides methods for CRUD operations and specific queries for Resident entities.
 */
public class ResidentDao {

    /**
     * Retrieves all Resident entities from the database.
     *
     * @return a list of all Resident entities.
     * @throws RuntimeException if an error occurs while fetching residents.
     */
    public List<Resident> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Resident", Resident.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all residents", e);
        }
    }

    /**
     * Retrieves a Resident entity by its ID.
     *
     * @param id the ID of the Resident to retrieve.
     * @return the Resident entity with the specified ID.
     * @throws IllegalArgumentException if no Resident is found with the given ID.
     * @throws RuntimeException if an error occurs while fetching the Resident.
     */
    public Resident findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Resident resident = session.get(Resident.class, id);
            if (resident == null) {
                throw new IllegalArgumentException("Resident not found with ID: " + id);
            }
            return resident;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch resident by ID", e);
        }
    }

    /**
     * Saves a new Resident entity to the database.
     *
     * @param resident the Resident entity to save.
     * @throws RuntimeException if an error occurs while saving the Resident.
     */
    public void save(Resident resident) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(resident);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save resident", e);
        }
    }

    /**
     * Updates an existing Resident entity in the database.
     *
     * @param resident the Resident entity with updated data.
     * @throws RuntimeException if an error occurs while updating the Resident.
     */
    public void update(Resident resident) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(resident);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update resident", e);
        }
    }

    /**
     * Deletes a Resident entity from the database by its ID.
     *
     * @param id the ID of the Resident to delete.
     * @throws IllegalArgumentException if no Resident is found with the given ID.
     * @throws RuntimeException if an error occurs while deleting the Resident.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Resident resident = session.get(Resident.class, id);
            if (resident == null) {
                throw new IllegalArgumentException("Resident not found with ID: " + id);
            }
            session.remove(resident);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete resident", e);
        }
    }

    /**
     * Retrieves a list of Resident entities associated with a specific Apartment ID.
     *
     * @param apartmentId the ID of the Apartment.
     * @return a list of Resident entities associated with the specified Apartment.
     * @throws RuntimeException if an error occurs while fetching residents.
     */
    public List<Resident> findByApartmentId(Long apartmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Resident r WHERE r.apartment.id = :apartmentId", Resident.class)
                    .setParameter("apartmentId", apartmentId)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch residents by apartment ID", e);
        }
    }
}
