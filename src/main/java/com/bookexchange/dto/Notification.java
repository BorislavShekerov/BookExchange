package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 11/16/2015.
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "MESSAGE")
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NOTIFIED")
    @JsonIgnore
    private User userNotified;
    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUserNotified() {
        return userNotified;
    }

    public void setUserNotified(User userNotified) {
        this.userNotified = userNotified;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
