package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.itcompany.models.company.Company;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT comp FROM Company comp WHERE comp.name LIKE %:companyName%")
    List<Company> findByNameContaining(@Param("companyName")String companyName);
}
