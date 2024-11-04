package com.project.realtime_ticketing_system.models;


public class UserTemplate {

    private Long id;
    private String name;
    private String email;
    private String passwordHash;

    public UserTemplate(Long id, String name, String email, String passwordHash) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
