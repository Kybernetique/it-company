package ru.ulstu.is.sbapp.itcompany.services;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id) {
        super(String.format("Project with id [%s] is not found", id));
    }
}
