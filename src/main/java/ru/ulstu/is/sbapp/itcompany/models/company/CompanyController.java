package ru.ulstu.is.sbapp.itcompany.models.company;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.itcompany.services.CompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public CompanyDTO getCompany(@PathVariable Long id) {
        return new CompanyDTO(companyService.findCompany(id));
    }

    @GetMapping("/")
    public List<CompanyDTO> getCompanies() {
        return companyService.findAllCompanies().stream()
                .map(CompanyDTO::new)
                .toList();
    }

    @PostMapping("/")
    public CompanyDTO createCompany(@RequestBody @Valid CompanyDTO companyDTO) {
        return new CompanyDTO(companyService.addCompany(companyDTO.getName(), companyDTO.getCountry()));
    }

    @PatchMapping("/{id}")
    public CompanyDTO updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyDTO companyDto) {
        return new CompanyDTO(companyService.updateCompany(id, companyDto.getName(), companyDto.getCountry()));
    }

    @DeleteMapping("/{id}")
    public CompanyDTO deleteCompany(@PathVariable Long id) {
        return new CompanyDTO(companyService.deleteCompany(id));
    }
}