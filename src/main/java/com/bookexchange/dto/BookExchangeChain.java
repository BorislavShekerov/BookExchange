package com.bookexchange.dto;

import com.bookexchange.graph.Graph;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sheke on 1/29/2016.
 */
@Entity
@Table(name = "BOOK_EXCHANGE_CHAINS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class BookExchangeChain {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INITIATOR_EMAIL", nullable = false)
    private User exchangeInitiator;
    @Column(name = "SUCCESSFUL")
    private boolean isSuccessful;
    @Column(name = "IS_OVER")
    private boolean isOver;
    @OneToMany( mappedBy = "chainRequestedIn",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<BookRequestedInChain> booksRequestedInChain = new ArrayList<>();
    @OneToMany( mappedBy = "parentExchangeChain",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<ExchangeChainRequest> exchangeChainRequests = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHAIN_BREAKER_EMAIL", referencedColumnName = "EMAIL")
    private User chainBreaker;
    @Column(name = "DATE_POSTED")
    private LocalDateTime dateCreated;
    @Column(name = "DATE_COMPLETED")
    private LocalDateTime dateCompleted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CLOSED_COMPONENT_GRAPH_ID")
    private Graph closedComponent;
    public User getExchangeInitiator() {
        return exchangeInitiator;
    }

    public List<ExchangeChainRequest> getExchangeChainRequests() {
        return exchangeChainRequests;
    }

    public void setExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
        this.exchangeChainRequests = exchangeChainRequests;
    }

    public List<BookRequestedInChain> getBooksRequestedInChain() {
        return booksRequestedInChain;
    }

    public void setBooksRequestedInChain(List<BookRequestedInChain> booksRequestedInChain) {
        this.booksRequestedInChain = booksRequestedInChain;
    }

    public void addRequestedBook(BookRequestedInChain bookRequestedInChain){
        this.booksRequestedInChain.add(bookRequestedInChain);
    }
    public void setExchangeInitiator(User exchangeInitiator) {
        this.exchangeInitiator = exchangeInitiator;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getChainBreaker() {
        return chainBreaker;
    }

    public void setChainBreaker(User chainBreaker) {
        this.chainBreaker = chainBreaker;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getLastBookInChainOwnerEmail(){
        return booksRequestedInChain.stream().filter(bookRequestedInChain -> bookRequestedInChain.getRequestedBy().equals(exchangeInitiator)).map(BookRequestedInChain::getRequestedBookOwnerEmail).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookExchangeChain that = (BookExchangeChain) o;

        if (isSuccessful != that.isSuccessful) return false;
        if (!exchangeInitiator.equals(that.exchangeInitiator)) return false;

        return exchangeChainRequests.equals(that.exchangeChainRequests);

    }

    @Override
    public int hashCode() {
        int result = exchangeInitiator.hashCode();
        result = 31 * result + (isSuccessful ? 1 : 0);
        result = 31 * result + exchangeChainRequests.hashCode();
        return result;
    }

    public Optional<ExchangeChainRequest> getExchangeChainRequestForUser(String userEmail){
        return this.exchangeChainRequests.stream().filter(exchangeChainRequest -> exchangeChainRequest.getRequestFor().getEmail().equals(userEmail)).findFirst();
    }

    public void markExchangeChainRequestAcceptedFromUser(String userEmail) {
        ExchangeChainRequest exchangeChainRequest = getExchangeChainRequestForUser(userEmail).get();

        exchangeChainRequest.setAccepted(true);
        exchangeChainRequest.setAnswered(true);
    }

    public boolean areAllChainRequestsAccepted() {
        return this.exchangeChainRequests.stream().filter(exchangeChainRequest -> !exchangeChainRequest.isAccepted()).count() == 0;
    }

    public List<User> getUsersToBeNotified() {
        return this.getExchangeChainRequests().stream().map(exchangeChainRequest1 -> exchangeChainRequest1.getRequestFor()).collect(Collectors.toList());
    }

    public Graph getClosedComponent() {
        return closedComponent;
    }

    public void setClosedComponent(Graph closedComponent) {
        this.closedComponent = closedComponent;
    }

    public void markExchangeChainRequestRejectedFromUser(String userRejectingEmail) {
        ExchangeChainRequest exchangeChainRequest = getExchangeChainRequestForUser(userRejectingEmail).get();

        exchangeChainRequest.setAccepted(false);
        exchangeChainRequest.setAnswered(true);
    }

    public static class BookExchangeChainBuilder{
        private BookExchangeChain bookExchangeChain;

        public BookExchangeChainBuilder(){
            bookExchangeChain = new BookExchangeChain();
        }

        public BookExchangeChainBuilder setId(int id) {
            bookExchangeChain.setId(id); return this;
        }

        public BookExchangeChainBuilder setExchangeInitiator(User initiator) {
            bookExchangeChain.setExchangeInitiator(initiator); return this;
        }


        public BookExchangeChainBuilder setExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
            bookExchangeChain.setExchangeChainRequests(exchangeChainRequests); return this;
        }

        public BookExchangeChainBuilder setDateCreated(LocalDateTime dateCreated){
            bookExchangeChain.setDateCreated(dateCreated);
            return this;
        }

        public BookExchangeChainBuilder setClosedComponent(Graph closedComponent) {
            bookExchangeChain.setClosedComponent(closedComponent);
            return this;
        }

        public BookExchangeChain buildBookExchangeChain(){
            return bookExchangeChain;
        }
    }


}
