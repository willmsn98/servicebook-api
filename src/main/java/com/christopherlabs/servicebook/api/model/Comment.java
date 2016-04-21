package com.christopherlabs.servicebook.api.model;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.security.checks.prefab.Role;

import javax.persistence.*;

@Entity
@Include(rootLevel = false)
@SharePermission(any = { Role.ALL.class })
public class Comment {

    private long id;
    private String text;
    private User user;
    private Event event;
    //private Timestamp timestamp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
