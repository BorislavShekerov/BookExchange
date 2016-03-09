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
    private User userOfferingTo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_CHOOSING_EMAIL", nullable = false)
    private User userChoosingFrom;
    @Column(name = "ACCEPTED")
    private boolean isAccepted;
    @Column(name = "ANSWERED")
    private boolean isAnswered;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHAIN_EXCHANGE_ID", nullable = false)
    private BookExchangeChain parentExchangeChain;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REQUEST_FOR", nullable = false)
    private User requestFor;

    public ExchangeChainRequest() {
    }

    public ExchangeChainRequest(User userOffering) {
        this.userOfferingTo = userOffering;
    }

    public User getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(User requestFor) {
        this.requestFor = requestFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookExchangeChain getParentExchangeChain() {
        return parentExchangeChain;
    }

    public void setParentExchangeChain(BookExchangeChain parentExchangeChain) {
        this.parentExchangeChain = parentExchangeChain;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    public User getUserOfferingTo() {
        return userOfferingTo;
    }

    public void setUserOfferingTo(User userOfferingTo) {
        this.userOfferingTo = userOfferingTo;
    }

    public User getUserChoosingFrom() {
        return userChoosingFrom;
    }

    public void setUserChoosingFrom(User userChoosingFrom) {
        this.userChoosingFrom = userChoosingFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeChainRequest that = (ExchangeChainRequest) o;

        if (!userOfferingTo.equals(that.userOfferingTo)) return false;
        return userChoosingFrom.equals(that.userChoosingFrom);

    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    @Override
    public int hashCode() {
        int result = userOfferingTo.hashCode();
        result = 31 * result + userChoosingFrom.hashCode();
        return result;
    }

    public static class ExchangeChainRequestBuilder{
        private ExchangeChainRequest exchangeChainRequest;

        public ExchangeChainRequestBuilder(){
            exchangeChainRequest = new ExchangeChainRequest();

        }
        public ExchangeChainRequestBuilder setRequestFor(User requestFor) {
            exchangeChainRequest.setRequestFor(requestFor);
            return this;
        }

        public ExchangeChainRequestBuilder setParentExchangeChain(BookExchangeChain parentExchangeChain) {
           exchangeChainRequest.setParentExchangeChain(parentExchangeChain);return this;
        }

        public ExchangeChainRequestBuilder setUserOfferingTo(User userOfferingTo) {
            exchangeChainRequest.setUserOfferingTo(userOfferingTo); return this;
        }

        public ExchangeChainRequestBuilder setUserChoosingFrom(User userChoosingFrom) {
            exchangeChainRequest.setUserChoosingFrom(userChoosingFrom); return this;
        }

        public ExchangeChainRequest build(){
            return exchangeChainRequest;
        }
    }
}
