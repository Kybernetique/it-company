package ru.ulstu.is.sbapp.itcompany.services;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(Long id) {
        super(String.format("Company with id [%s] is not found", id));
    }
}
