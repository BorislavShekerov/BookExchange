package com.bookexchange.dto;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 2/5/2016.
 */
@Entity
@Table(name = "BOOKS_REQUESTED_IN_CHAINS")
public class BookRequestedInChain{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BOOK_ID")
    private Book requestedBook;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "REQUESTED_BY", referencedColumnName = "EMAIL")
    private User requestedBy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHAIN_REQUESTED_IN", nullable = false)
    private BookExchangeChain chainRequestedIn;


    public BookRequestedInChain(){
        super();
    }

    public BookRequestedInChain(Book book){
        this.requestedBook = book;
    }
    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public BookExchangeChain getChainRequestedIn() {
        return chainRequestedIn;
    }

    public void setChainRequestedIn(BookExchangeChain chainRequestedIn) {
        this.chainRequestedIn = chainRequestedIn;
    }

    public Book getRequestedBook() {
        return requestedBook;
    }

    public void setRequestedBook(Book requestedBook) {
        this.requestedBook = requestedBook;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestedBookOwnerEmail() {
        return getRequestedBook().getPostedBy().getEmail();
    }

    public static class BookRequestedInChainBuilder{
        private BookRequestedInChain bookRequestedInChain;

        public BookRequestedInChainBuilder(){
            bookRequestedInChain = new BookRequestedInChain();
        }

        public BookRequestedInChainBuilder setRequestedBy(User initiator) {
            bookRequestedInChain.setRequestedBy(initiator); return this;
        }


        public BookRequestedInChainBuilder setRequestedBook(Book bookRequested) {
            bookRequestedInChain.setRequestedBook(bookRequested); return this;
        }

        public BookRequestedInChainBuilder setChainRequestedIn(BookExchangeChain bookExchangeChain){
            bookRequestedInChain.setChainRequestedIn(bookExchangeChain);
            return this;
        }

        public BookRequestedInChain buildBookExchangeChain(){
            return bookRequestedInChain;
        }
    }
}
