package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 11/13/2015.
 */
@Entity
@Table(name = "BOOK_EXCHANGES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DirectBookExchange {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_REQUESTED_ID")
    protected Book bookRequested;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXCHANGE_INITIATOR", nullable = false)
    private User exchangeInitiator;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_OFFERED_IN_EXCHANGE")
    protected Book bookOfferedInExchange;
    @Column(name = "DATE_POSTED")
    private LocalDateTime dateCreated;
    @Column(name = "DATE_COMPLETED")
    private LocalDateTime dateCompleted;
    @Column(name = "IS_SUCCESSFUL")
    private boolean isSuccessful;
    @Column(name = "IS_OVER")
    private boolean isOver;

    public Book getBookRequested() {
        return bookRequested;
    }

    public void setBookRequested(Book bookRequested) {
        this.bookRequested = bookRequested;
    }

    public Book getBookOfferedInExchange() {
        return bookOfferedInExchange;
    }

    public void setBookOfferedInExchange(Book bookOfferedInExchange) {
        this.bookOfferedInExchange = bookOfferedInExchange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getExchangeInitiatorEmail(){
        return exchangeInitiator.getEmail();
    }

    public User getUserUnderOffer(){
        return bookRequested.getPostedBy();
    }

    public User getExchangeInitiator() {
        return exchangeInitiator;
    }

    public void setExchangeInitiator(User exchangeInitiator) {
        this.exchangeInitiator = exchangeInitiator;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectBookExchange)) return false;

        DirectBookExchange that = (DirectBookExchange) o;

        if (getBookRequested() != null ? !getBookRequested().equals(that.getBookRequested()) : that.getBookRequested() != null)
            return false;
        return !(getBookOfferedInExchange() != null ? !getBookOfferedInExchange().equals(that.getBookOfferedInExchange()) : that.getBookOfferedInExchange() != null);

    }

    @Override
    public int hashCode() {
        int result = getBookRequested() != null ? getBookRequested().hashCode() : 0;
        result = 31 * result + (getBookOfferedInExchange() != null ? getBookOfferedInExchange().hashCode() : 0);
        return result;
    }


    public static class BookExchangeBuilder{
        private DirectBookExchange directBookExchange;

        public BookExchangeBuilder(){
            directBookExchange = new DirectBookExchange();
        }

        public BookExchangeBuilder setId(int id) {
            directBookExchange.setId(id); return this;
        }

        public BookExchangeBuilder setBookRequested(Book bookPostedOnExchange) {
            directBookExchange.setBookRequested(bookPostedOnExchange); return this;
        }


        public BookExchangeBuilder setBookOfferedInExchange(Book bookOfferedInExchange) {
            directBookExchange.setBookOfferedInExchange(bookOfferedInExchange); return this;
        }

        public BookExchangeBuilder setDateCreated(LocalDateTime dateCreated) {
            directBookExchange.setDateCreated(dateCreated); return this;
        }

        public BookExchangeBuilder setExchangeInitiator(User exchangeInitiator){
            directBookExchange.setExchangeInitiator(exchangeInitiator); return this;
        }

        public DirectBookExchange buildBookExchange(){
            return directBookExchange;
        }
    }

}
