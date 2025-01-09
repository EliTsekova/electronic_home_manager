package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.CompanyDao;
import org.electronic_home_manager.entity.Company;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public List<Company> getAllCompanies() {
        return companyDao.findAll();
    }

    public Company getCompanyById(int id) {
        return companyDao.findById(id);
    }

    public void createCompany(Company company) {
        companyDao.save(company);
    }

    public void updateCompany(Company company) {
        companyDao.update(company);
    }

    public void deleteCompany(int id) {
        companyDao.delete(id);
    }
    /*public List<Company> sortCompaniesByRevenue() {
        return companyDao.findAll().stream()
                .sorted(Comparator.comparing(Company::calculateRevenue).reversed())
                .collect(Collectors.toList());
    }*/

}
