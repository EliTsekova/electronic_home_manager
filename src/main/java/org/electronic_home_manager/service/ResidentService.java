package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.ResidentDao;
import org.electronic_home_manager.entity.Resident;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ResidentService {
    private final ResidentDao residentDao;
    private final Map<Long, Resident> residentCache = new ConcurrentHashMap<>();

    public ResidentService(ResidentDao residentDao) {
        this.residentDao = residentDao;
    }

    public List<Resident> getAllResidents() {
        return residentDao.findAll();
    }

    public Resident getResidentById(Long id) {
        return residentCache.computeIfAbsent(id, key -> {
            Resident resident = residentDao.findById(key);
            if (resident == null) {
                throw new IllegalArgumentException("Resident not found with ID: " + id);
            }
            return resident;
        });
    }

    public void addResident(Resident resident) {
        if (resident == null || resident.getName() == null || resident.getName().isEmpty() ||
                resident.getAge() < 0 || resident.getApartment() == null) {
            throw new IllegalArgumentException("Invalid resident data provided.");
        }

        residentDao.save(resident);
    }


    public void updateResident(Resident resident) {
        if (residentDao.findById(resident.getId()) == null) {
            throw new IllegalArgumentException("Cannot update. Resident not found with ID: " + resident.getId());
        }
        residentDao.update(resident);
        residentCache.put(resident.getId(), resident);
    }

    public void deleteResident(Long id) {
        Resident resident = getResidentById(id);
        residentDao.delete(id);
        residentCache.remove(id);
    }

    public List<Resident> filterResidentsByAge(int minAge) {
        return residentDao.findAll().stream()
                .filter(resident -> resident.getAge() > minAge)
                .collect(Collectors.toList());
    }

    public List<Resident> sortResidentsByName() {
        return residentDao.findAll().stream()
                .sorted(Comparator.comparing(Resident::getName))
                .collect(Collectors.toList());
    }

    public List<Resident> sortResidentsByAge() {
        return residentDao.findAll().stream()
                .sorted(Comparator.comparingInt(Resident::getAge))
                .collect(Collectors.toList());
    }
}
