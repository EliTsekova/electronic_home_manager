package org.electronic_home_manager.dao;

import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for managing Building entities.
 * Provides methods to perform CRUD operations and conversions to DTOs.
 */
public class BuildingDao {

    /**
     * Retrieves all Building entities from the database and converts them to CreateBuildingDto objects.
     *
     * @return a list of CreateBuildingDto objects representing all buildings.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public List<CreateBuildingDto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Building", Building.class)
                    .list()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all buildings", e);
        }
    }

    /**
     * Retrieves a Building entity by its ID.
     *
     * @param id the ID of the building to retrieve.
     * @return the Building entity with the specified ID.
     * @throws IllegalArgumentException if no building is found with the given ID.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public Building findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, id);
            if (building == null) {
                throw new IllegalArgumentException("Building not found with ID: " + id);
            }
            return building;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch building by ID", e);
        }
    }

    /**
     * Saves a new Building entity to the database.
     *
     * @param building the Building entity to save.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void save(Building building) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(building);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save building: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing Building entity in the database.
     *
     * @param building the CreateBuildingDto containing the updated building data.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void update(CreateBuildingDto building) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(building);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to update building", e);
        }
    }

    /**
     * Deletes a Building entity from the database by its ID.
     *
     * @param id the ID of the building to delete.
     * @throws IllegalArgumentException if no building is found with the given ID.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Building building = session.get(Building.class, id);
            if (building == null) {
                throw new IllegalArgumentException("Building not found with ID: " + id);
            }
            session.remove(building);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to delete building", e);
        }
    }

    /**
     * Converts a Building entity to a CreateBuildingDto object.
     *
     * @param building the Building entity to convert.
     * @return a CreateBuildingDto representing the Building entity.
     */
    public CreateBuildingDto toDto(Building building) {
        return new CreateBuildingDto(
                building.getId(),
                building.getAddress(),
                building.getFloors(),
                building.getCommonArea(),
                building.getCompany() != null ? building.getCompany().getId() : null
        );
    }
}
