package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.ApartmentDao;
import org.electronic_home_manager.dto.ApartmentDto;
import org.electronic_home_manager.entity.Apartment;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Resident;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Apartment-related operations.
 * Handles the business logic for apartments and communicates with the DAO layer.
 */
public class ApartmentService {
    private final ApartmentDao apartmentDao;

    /**
     * Constructor to initialize ApartmentService with the DAO layer.
     *
     * @param apartmentDao The ApartmentDao instance.
     */
    public ApartmentService(ApartmentDao apartmentDao) {
        this.apartmentDao = apartmentDao;
    }

    /**
     * Retrieves all apartments as DTOs.
     *
     * @return List of ApartmentDto.
     */
    public List<ApartmentDto> getAllApartments() {
        return apartmentDao.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single apartment by ID as a DTO.
     *
     * @param id The apartment ID.
     * @return ApartmentDto.
     */
    public ApartmentDto getApartmentById(Long id) {
        Apartment apartment = apartmentDao.findById(id);
        return toDto(apartment);
    }

    /**
     * Adds a new apartment to a building.
     *
     * @param apartmentDto Apartment data.
     * @param building     The building to which the apartment belongs.
     */
    public void addApartment(ApartmentDto apartmentDto, Building building) {
        Apartment apartment = toEntity(apartmentDto);
        apartment.setBuilding(building);
        validateApartmentData(apartment);
        apartmentDao.save(apartment);

        apartmentDto.setId(apartment.getId());
    }

    /**
     * Updates an existing apartment with new data.
     *
     * @param apartmentDto Apartment data with updated values.
     */
    public void updateApartment(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentDao.findById(apartmentDto.getId());
        apartment.setNumber(apartmentDto.getNumber());
        apartment.setArea(apartmentDto.getArea());
        apartment.setHasPet(apartmentDto.isHasPet());
        validateApartmentData(apartment);
        apartmentDao.update(apartment);
    }

    /**
     * Deletes an apartment by ID.
     *
     * @param id The apartment ID.
     */
    public void deleteApartment(Long id) {
        apartmentDao.delete(id);
    }

    /**
     * Calculates the total monthly fee for an apartment.
     *
     * @param apartment The apartment entity.
     * @return Total monthly fee.
     */
    public BigDecimal calculateMonthlyFee(Apartment apartment) {
        BigDecimal baseFee = apartment.getArea().multiply(new BigDecimal("0.5"));

        if (apartment.getResidents() != null) {
            for (Resident resident : apartment.getResidents()) {
                baseFee = baseFee.add(BigDecimal.valueOf(resident.calculateElevatorFee()));
            }
        }

        if (apartment.isHasPet()) {
            baseFee = baseFee.add(new BigDecimal("2.0"));
        }

        return baseFee;
    }

    /**
     * Assigns residents to an apartment.
     *
     * @param apartmentId Apartment ID.
     * @param residents   List of residents.
     */
    public void assignResidentsToApartment(Long apartmentId, List<Resident> residents) {
        Apartment apartment = apartmentDao.findById(apartmentId);
        apartment.setResidents(residents);
        apartmentDao.update(apartment);
    }

    /**
     * Assigns an owner to an apartment.
     *
     * @param apartmentId Apartment ID.
     * @param owner       Owner resident.
     */
    public void assignOwnerToApartment(Long apartmentId, Resident owner) {
        Apartment apartment = apartmentDao.findById(apartmentId);
        apartment.setOwner(owner);
        apartmentDao.update(apartment);
    }

    /**
     * Validates apartment data.
     * Ensures that the apartment has valid area, number, and is associated with a building.
     *
     * @param apartment The apartment entity.
     * @throws IllegalArgumentException if validation fails.
     */
    public void validateApartmentData(Apartment apartment) {
        if (apartment.getArea() == null || apartment.getArea().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Apartment area must be positive.");
        }
        if (apartment.getNumber() <= 0) {
            throw new IllegalArgumentException("Apartment number must be positive.");
        }
        if (apartment.getBuilding() == null) {
            throw new IllegalArgumentException("Apartment must belong to a building.");
        }
    }

    /**
     * Converts an Apartment entity to a DTO.
     *
     * @param apartment The Apartment entity.
     * @return ApartmentDto.
     */
    private ApartmentDto toDto(Apartment apartment) {
        return new ApartmentDto(
                apartment.getId(),
                apartment.getNumber(),
                apartment.getArea(),
                apartment.isHasPet(),
                apartment.getBuilding() != null ? apartment.getBuilding().getId() : null
        );
    }

    /**
     * Converts a DTO to an Apartment entity.
     *
     * @param dto The ApartmentDto.
     * @return Apartment entity.
     */
    private Apartment toEntity(ApartmentDto dto) {
        Apartment apartment = new Apartment();
        apartment.setId(dto.getId());
        apartment.setNumber(dto.getNumber());
        apartment.setArea(dto.getArea());
        apartment.setHasPet(dto.isHasPet());
        return apartment;
    }
}

