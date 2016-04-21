package com.christopherlabs.servicebook.api.model;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.security.checks.prefab.Role;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Include(rootLevel = true)
@SharePermission(any = { Role.ALL.class })
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private Organization organization;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private Collection<Event> ownedEvents;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToOne
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(unique = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(mappedBy = "owner")
    public Collection<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Collection<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }
}
