package com.christopherlabs.service.model;

import com.yahoo.elide.annotation.Include;

import javax.persistence.*;

@Entity
@Include(rootLevel = true)
public class Photo {

    private long id;
    private String image;
    private User owner;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
