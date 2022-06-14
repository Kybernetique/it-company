package ru.ulstu.is.sbapp.itcompany.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.company.CompanyDTO;
import ru.ulstu.is.sbapp.itcompany.models.company.Company;
import ru.ulstu.is.sbapp.itcompany.repositories.CompanyRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;
    private final ValidatorUtil validatorUtil;

    public CompanyService(CompanyRepository companyRepository, ValidatorUtil validatorUtil) {
        this.companyRepository = companyRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Company addCompany(String name, String country) {
        if(!StringUtils.hasText(name) || !StringUtils.hasText(country)) {
            throw new IllegalArgumentException("Company data is null or empty");
        }
        final Company company = new Company(name, country);
        validatorUtil.validate(company);
        return companyRepository.save(company);
    }

    @Transactional
    public CompanyDTO addCompany(CompanyDTO companyDTO) {
        return new CompanyDTO(addCompany(companyDTO.getName(), companyDTO.getCountry()));
    }

    @Transactional(readOnly = true)
    public Company findCompany(Long id) {
        final Optional<Company> company = companyRepository.findById(id);
        return company.orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    public Company updateCompany(Long id, String name, String country) {
        if(!StringUtils.hasText(name) || !StringUtils.hasText(country)) {
            throw new IllegalArgumentException("Company data is null or empty");
        }
        final Company currentCompany = findCompany(id);
        currentCompany.setName(name);
        currentCompany.setCountry(country);
        validatorUtil.validate(currentCompany);
        return companyRepository.save(currentCompany);
    }

    @Transactional
    public CompanyDTO updateCompany(CompanyDTO companyDTO) {
        return new CompanyDTO(updateCompany(companyDTO.getId(), companyDTO.getName(), companyDTO.getCountry()));
    }

    @Transactional
    public Company deleteCompany(Long id) {
        Company currentCompany = findCompany(id);
        companyRepository.delete(currentCompany);
        return currentCompany;
    }

    @Transactional
    public void deleteAllCompanies() throws InCompanyFoundDevelopersException {
        List<Company> companies = findAllCompanies();
        for(var company : companies){
            if(company.getDevelopers().size() > 0)
                throw new InCompanyFoundDevelopersException("У " + company.getName() + " " + company.getCountry() + " есть автомобили");
        }
        companyRepository.deleteAll();
    }
}
