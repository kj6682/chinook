package org.kj6682.hop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username, password;
    private boolean active;

    protected Account() {// why JPA why?
    }

    public Account(String username, String password, boolean active) {

        this.username = username;
        this.password = password;
        this.active = active;
    }

    public Long getId() {

        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }
}
