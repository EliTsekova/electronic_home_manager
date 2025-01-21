package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Contract;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Data Access Object (DAO) for managing Contract entities.
 * Provides methods for CRUD operations on Contract objects.
 */
public class ContractDao {

    /**
     * Retrieves all Contract entities from the database.
     *
     * @return a list of all Contract entities.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public List<Contract> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Contract", Contract.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all contracts", e);
        }
    }

    /**
     * Retrieves a Contract entity by its ID.
     *
     * @param id the ID of the contract to retrieve.
     * @return the Contract entity with the specified ID, or null if not found.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public Contract findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Contract.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch contract by ID", e);
        }
    }

    /**
     * Saves a new Contract entity to the database.
     *
     * @param contract the Contract entity to save.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void save(Contract contract) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(contract);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save contract", e);
        }
    }

    /**
     * Updates an existing Contract entity in the database.
     *
     * @param contract the Contract entity containing updated data.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void update(Contract contract) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(contract);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update contract", e);
        }
    }

    /**
     * Deletes a Contract entity from the database by its ID.
     *
     * @param id the ID of the contract to delete.
     * @throws IllegalArgumentException if no contract is found with the given ID.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Contract contract = session.get(Contract.class, id);
            if (contract == null) {
                throw new IllegalArgumentException("Contract not found with ID: " + id);
            }
            session.remove(contract);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete contract", e);
        }
    }
}
