package ru.ulstu.is.sbapp.itcompany.services;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(Long id) {
        super(String.format("Job with id [%s] is not found", id));
    }
}
