package org.electronic_home_manager.dao;

import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for managing Employee entities.
 * Provides methods for CRUD operations and additional queries on Employee entities.
 */
public class EmployeeDao {

    /**
     * Retrieves all Employee entities as DTOs from the database.
     *
     * @return a list of all employees as DTOs.
     * @throws RuntimeException if fetching employees fails.
     */
    public List<EmployeeDto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class)
                    .list()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all employees", e);
        }
    }

    /**
     * Finds an Employee entity by its ID and returns it as a DTO.
     *
     * @param id the ID of the employee to retrieve.
     * @return the found Employee entity as a DTO.
     * @throws IllegalArgumentException if no employee is found with the given ID.
     * @throws RuntimeException if fetching the employee fails.
     */
    public EmployeeDto findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with ID: " + id);
            }
            return toDto(employee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employee by ID", e);
        }
    }

    /**
     * Saves a new Employee entity to the database using a DTO.
     *
     * @param employeeDto the Employee DTO to save.
     * @throws RuntimeException if the save operation fails.
     */
    public void save(EmployeeDto employeeDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(toEntity(employeeDto));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    /**
     * Updates an existing Employee entity in the database using a DTO.
     *
     * @param employeeDto the Employee DTO with updated values.
     * @throws RuntimeException if the update operation fails.
     */
    public void update(EmployeeDto employeeDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(toEntity(employeeDto));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    /**
     * Deletes an Employee entity from the database by its ID.
     *
     * @param id the ID of the employee to delete.
     * @throws IllegalArgumentException if no employee is found with the given ID.
     * @throws RuntimeException if the delete operation fails.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with ID: " + id);
            }
            session.remove(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    /**
     * Finds the Employee with the least number of buildings assigned and returns it as a DTO.
     *
     * @return the Employee DTO with the least buildings or null if no employees exist.
     * @throws RuntimeException if the query operation fails.
     */
    public EmployeeDto findEmployeeWithLeastBuildings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e LEFT JOIN e.buildings b GROUP BY e ORDER BY COUNT(b.id) ASC", Employee.class)
                    .setMaxResults(1)
                    .uniqueResult();
            return employee != null ? toDto(employee) : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find employee with least buildings", e);
        }
    }

    // Conversion helpers

    /**
     * Converts an Employee entity to a DTO.
     *
     * @param employee the Employee entity to convert.
     * @return the converted EmployeeDto.
     */
    private EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone()
        );
    }

    /**
     * Converts an Employee DTO to an entity.
     *
     * @param dto the EmployeeDto to convert.
     * @return the converted Employee entity.
     */
    public Employee toEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        return employee;
    }
}
