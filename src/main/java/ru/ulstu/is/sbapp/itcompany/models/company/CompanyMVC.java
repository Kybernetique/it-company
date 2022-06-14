package ru.ulstu.is.sbapp.itcompany.models.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.services.CompanyService;

import javax.validation.Valid;

@Controller
@RequestMapping("/company")
public class CompanyMVC {
    private final CompanyService companyService;

    public CompanyMVC(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String getCompanies(Model model) {
        model.addAttribute("companies",
                companyService.findAllCompanies().stream()
                        .map(CompanyDTO::new)
                        .toList());
        return "company";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editCompany(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("companyDto", new CompanyDTO());
        }
        else {
            model.addAttribute("companyId", id);
            model.addAttribute("companyDto", new CompanyDTO(companyService.findCompany(id)));
            model.addAttribute("developers", companyService.findCompany(id).getDevelopers());
        }
        return "company-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveCompany(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid CompanyDTO companyDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "company-edit";
        }
        if (id == null || id <= 0) {
            companyService.addCompany(companyDTO.getName(), companyDTO.getCountry());
        } else {
            companyService.updateCompany(id, companyDTO.getName(), companyDTO.getCountry());
        }
        return "redirect:/company";
    }

    @PostMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return "redirect:/company";
    }
}
