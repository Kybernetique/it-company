package ru.ulstu.is.sbapp.itcompany.services;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(Long id) {
        super(String.format("Developer with id [%s] is not found", id));
    }
}
