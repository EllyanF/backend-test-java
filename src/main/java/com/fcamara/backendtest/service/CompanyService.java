package com.fcamara.backendtest.service;

import com.fcamara.backendtest.domain.company.Company;
import com.fcamara.backendtest.domain.enums.UpdateParkingLotOperators;
import com.fcamara.backendtest.domain.enums.VehicleType;
import com.fcamara.backendtest.dto.company.CompanyDTO;
import com.fcamara.backendtest.dto.company.UpdateCompanyDTO;
import com.fcamara.backendtest.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Company createCompany(CompanyDTO companyDTO) {
        Company company = new Company(companyDTO);

        return companyRepository.save(company);
    }

    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Company getById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Company updateCompany(Long id, UpdateCompanyDTO companyDTO) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Company company = this.companyRepository.findById(id).orElseThrow();
        modelMapper.map(companyDTO, company);

        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public void updateParkingLot(Company company, VehicleType type, UpdateParkingLotOperators operator) {
        company.getLotSpaces().updateLotSpaces(type, operator);
        companyRepository.save(company);
    }
}
