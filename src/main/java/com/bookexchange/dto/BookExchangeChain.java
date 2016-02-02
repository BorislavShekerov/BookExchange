package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "exchangeChainIncludedIn")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<BookExchange> exchangesInChain = new HashSet<>();
    @Column(name = "SUCCESSFUL")
    private boolean isSuccessful;
    @OneToMany( mappedBy = "requestFor",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<ExchangeChainRequest> exchangeChainRequests = new ArrayList<>();

    public User getExchangeInitiator() {
        return exchangeInitiator;
    }

    public List<ExchangeChainRequest> getExchangeChainRequests() {
        return exchangeChainRequests;
    }

    public void setExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
        this.exchangeChainRequests = exchangeChainRequests;
    }

    public void setExchangeInitiator(User exchangeInitiator) {
        this.exchangeInitiator = exchangeInitiator;
    }

    public Set<BookExchange> getExchangesInChain() {
        return exchangesInChain;
    }

    public void setExchangesInChain(Set<BookExchange> exchangesInChain) {
        this.exchangesInChain = exchangesInChain;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookExchangeChain that = (BookExchangeChain) o;

        if (isSuccessful != that.isSuccessful) return false;
        if (!exchangeInitiator.equals(that.exchangeInitiator)) return false;
        if (exchangesInChain != null ? !exchangesInChain.equals(that.exchangesInChain) : that.exchangesInChain != null)
            return false;
        return exchangeChainRequests.equals(that.exchangeChainRequests);

    }

    @Override
    public int hashCode() {
        int result = exchangeInitiator.hashCode();
        result = 31 * result + (exchangesInChain != null ? exchangesInChain.hashCode() : 0);
        result = 31 * result + (isSuccessful ? 1 : 0);
        result = 31 * result + exchangeChainRequests.hashCode();
        return result;
    }

    public static class BookExchangeChainBuilder{
        private BookExchangeChain bookExchangeChain;

        public BookExchangeChainBuilder(){
            bookExchangeChain = new BookExchangeChain();
        }

        public BookExchangeChainBuilder setExchangeInitiator(User initiator) {
            bookExchangeChain.setExchangeInitiator(initiator); return this;
        }


        public BookExchangeChainBuilder setExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
            bookExchangeChain.setExchangeChainRequests(exchangeChainRequests); return this;
        }

        public BookExchangeChainBuilder setExchangesInChain(Set<BookExchange> exchangesInChain) {
            bookExchangeChain.setExchangesInChain(exchangesInChain); return this;
        }

        public BookExchangeChain buildBookExchangeChain(){
            return bookExchangeChain;
        }
    }


}
