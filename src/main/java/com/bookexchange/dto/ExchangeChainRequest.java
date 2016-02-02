package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sheke on 1/29/2016.
 */
@Entity
@Table(name = "CHAIN_EXCHANGE_REQUEST")
public class ExchangeChainRequest {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_OFFERING_EMAIL", nullable = false)
    private User userOffering;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_CHOOSING_EMAIL", nullable = false)
    private User userChoosing;
    @Column(name = "ACCEPTED")
    private boolean isAccepted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHAIN_EXCHANGE_ID", nullable = false)
    private BookExchangeChain requestFor;

    public ExchangeChainRequest() {
    }

    public ExchangeChainRequest(User userOffering) {
        this.userOffering = userOffering;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookExchangeChain getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(BookExchangeChain requestFor) {
        this.requestFor = requestFor;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    public User getUserOffering() {
        return userOffering;
    }

    public void setUserOffering(User userOffering) {
        this.userOffering = userOffering;
    }

    public User getUserChoosing() {
        return userChoosing;
    }

    public void setUserChoosing(User userChoosing) {
        this.userChoosing = userChoosing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeChainRequest that = (ExchangeChainRequest) o;

        if (!userOffering.equals(that.userOffering)) return false;
        return userChoosing.equals(that.userChoosing);

    }

    @Override
    public int hashCode() {
        int result = userOffering.hashCode();
        result = 31 * result + userChoosing.hashCode();
        return result;
    }
}
