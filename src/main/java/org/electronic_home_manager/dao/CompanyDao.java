package org.electronic_home_manager.dao;

import org.electronic_home_manager.dto.CreateCompanyDto;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for managing Company entities.
 * Provides methods for CRUD operations and conversion between DTOs and entities.
 */
public class CompanyDao {

    /**
     * Retrieves all Company entities from the database and converts them to CreateCompanyDto objects.
     *
     * @return a list of CreateCompanyDto objects representing all companies.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public List<CreateCompanyDto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company", Company.class)
                    .list()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all companies", e);
        }
    }

    /**
     * Retrieves a Company entity by its ID and converts it to a CreateCompanyDto.
     *
     * @param id the ID of the company to retrieve.
     * @return a CreateCompanyDto representing the Company entity.
     * @throws IllegalArgumentException if no company is found with the given ID.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public CreateCompanyDto findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company company = session.get(Company.class, id);
            if (company == null) {
                throw new IllegalArgumentException("Company not found with ID: " + id);
            }
            return toDto(company);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch company by ID", e);
        }
    }

    /**
     * Saves a new Company entity to the database.
     *
     * @param companyDto the CreateCompanyDto containing company data.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void save(CreateCompanyDto companyDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Company company = toEntity(companyDto);
            session.persist(company);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save company", e);
        }
    }

    /**
     * Updates an existing Company entity in the database.
     *
     * @param companyDto the CreateCompanyDto containing updated company data.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void update(CreateCompanyDto companyDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Company company = toEntity(companyDto);
            session.merge(company);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update company", e);
        }
    }

    /**
     * Deletes a Company entity from the database by its ID.
     *
     * @param id the ID of the company to delete.
     * @throws IllegalArgumentException if no company is found with the given ID.
     * @throws RuntimeException if an error occurs during the database operation.
     */
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Company company = session.get(Company.class, id);
            if (company == null) {
                throw new IllegalArgumentException("Company not found with ID: " + id);
            }
            session.remove(company);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete company", e);
        }
    }

    /**
     * Converts a Company entity to a CreateCompanyDto.
     *
     * @param company the Company entity to convert.
     * @return a CreateCompanyDto representing the Company entity.
     */
    private CreateCompanyDto toDto(Company company) {
        return new CreateCompanyDto(company.getName(), company.getAddress(), company.getPhone(), company.getEmail());
    }

    /**
     * Converts a CreateCompanyDto to a Company entity.
     *
     * @param dto the CreateCompanyDto to convert.
     * @return a Company entity representing the data in the DTO.
     */
    public Company toEntity(CreateCompanyDto dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setPhone(dto.getPhone());
        company.setEmail(dto.getEmail());
        return company;
    }

}
