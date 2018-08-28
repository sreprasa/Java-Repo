package com.intv.checklist.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {


    @Id
    private String userId;

    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL_ID")
    private String email;

    @Column(name = "ACTIVE")
    private boolean active;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Checklist.class)
    private List<Checklist> checklists;

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}