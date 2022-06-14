package ru.ulstu.is.sbapp.itcompany.models.user;

public class UserDto {
    private final long id;
    private final String login;
    private final UserRole role;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.role = user.getRole();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }
}
