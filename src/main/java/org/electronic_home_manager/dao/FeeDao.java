package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Fee;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Data Access Object (DAO) for managing Fee entities.
 * Provides methods for CRUD operations and specific queries for Fee entities.
 */
public class FeeDao {

    /**
     * Retrieves all Fee entities from the database.
     *
     * @return a list of all Fee entities.
     * @throws RuntimeException if an error occurs while fetching fees.
     */
    public List<Fee> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Fee", Fee.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all fees", e);
        }
    }

    /**
     * Retrieves a Fee entity by its ID.
     *
     * @param id the ID of the Fee to retrieve.
     * @return the Fee entity with the specified ID.
     * @throws IllegalArgumentException if no Fee is found with the given ID.
     * @throws RuntimeException if an error occurs while fetching the Fee.
     */
    public Fee findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Fee fee = session.get(Fee.class, id);
            if (fee == null) {
                throw new IllegalArgumentException("Fee not found with ID: " + id);
            }
            return fee;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fee by ID", e);
        }
    }

    /**
     * Saves a new Fee entity to the database.
     *
     * @param fee the Fee entity to save.
     * @throws RuntimeException if an error occurs while saving the Fee.
     */
    public void save(Fee fee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(fee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save fee", e);
        }
    }

    /**
     * Updates an existing Fee entity in the database.
     *
     * @param fee the Fee entity with updated data.
     * @throws RuntimeException if an error occurs while updating the Fee.
     */
    public void update(Fee fee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(fee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update fee", e);
        }
    }

    /**
     * Deletes a Fee entity from the database by its ID.
     *
     * @param id the ID of the Fee to delete.
     * @throws IllegalArgumentException if no Fee is found with the given ID.
     * @throws RuntimeException if an error occurs while deleting the Fee.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Fee fee = session.get(Fee.class, id);
            if (fee == null) {
                throw new IllegalArgumentException("Fee not found with ID: " + id);
            }
            session.remove(fee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete fee", e);
        }
    }

    /**
     * Retrieves a list of Fee entities associated with a specific Building ID.
     *
     * @param buildingId the ID of the Building.
     * @return a list of Fee entities associated with the Building.
     * @throws RuntimeException if an error occurs while fetching the fees.
     */
    public List<Fee> findByBuilding(Long buildingId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Fee f WHERE f.building.id = :buildingId", Fee.class)
                    .setParameter("buildingId", buildingId)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fees by building ID", e);
        }
    }

    /**
     * Retrieves a list of Fee entities associated with a specific Company ID.
     *
     * @param companyId the ID of the Company.
     * @return a list of Fee entities associated with the Company.
     * @throws RuntimeException if an error occurs while fetching the fees.
     */
    public List<Fee> findByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Fee f WHERE f.company.id = :companyId", Fee.class)
                    .setParameter("companyId", companyId)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fees by company ID", e);
        }
    }
}
