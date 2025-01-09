package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.ApartmentDao;
import org.electronic_home_manager.entity.Apartment;
import org.electronic_home_manager.entity.Building;

import java.util.List;

public class ApartmentService {
    private final ApartmentDao apartmentDao;

    public ApartmentService(ApartmentDao apartmentDao) {
        this.apartmentDao = apartmentDao;
    }

    public List<Apartment> getAllApartments() {
        return apartmentDao.findAll();
    }

    public Apartment getApartmentById(int id) {
        return apartmentDao.findById(id);
    }

    public void addApartment(Apartment apartment, Building building) {
        apartment.setBuilding(building);
        apartmentDao.save(apartment);
    }

    public void updateApartment(Apartment apartment) {
        apartmentDao.update(apartment);
    }

    public void deleteApartment(int id) {
        apartmentDao.delete(id);
    }
}
