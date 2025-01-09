package org.electronic_home_manager.service;


import org.electronic_home_manager.dao.ResidentDao;
import org.electronic_home_manager.entity.Resident;

import java.util.List;
import java.util.stream.Collectors;

public class ResidentService {
    private final ResidentDao residentDao;

    public ResidentService(ResidentDao residentDao) {
        this.residentDao = residentDao;
    }

    public List<Resident> getAllResidents() {
        return residentDao.findAll();
    }

    public Resident getResidentById(int id) {
        return residentDao.findById(id);
    }

    public void addResident(Resident resident) {
        residentDao.save(resident);
    }

    public void updateResident(Resident resident) {
        residentDao.update(resident);
    }

    public void deleteResident(int id) {
        residentDao.delete(id);
    }

    public List<Resident> filterResidentsByAge(int minAge) {
        return residentDao.findAll().stream()
                .filter(resident -> resident.getAge() > minAge)
                .collect(Collectors.toList());
    }

}

