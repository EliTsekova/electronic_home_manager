package org.electronic_home_manager.dao;

import org.electronic_home_manager.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    public Employee findById(int id) {
        return entityManager.find(Employee.class, id);
    }

    public List<Employee> findAll() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    @Transactional
    public void update(Employee employee) {
        entityManager.merge(employee);
    }

    @Transactional
    public void delete(int id) {
        Employee employee = findById(id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    public Employee findEmployeeWithLeastBuildings() {
        return entityManager.createQuery(
                        "SELECT e FROM Employee e ORDER BY SIZE(e.buildings) ASC", Employee.class)
                .setMaxResults(1)
                .getSingleResult();
    }

}
