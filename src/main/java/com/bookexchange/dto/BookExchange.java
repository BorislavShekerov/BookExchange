package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sheke on 11/13/2015.
 */
@Entity
@Table(name = "BOOK_EXCHANGES")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="DTYPE",
        discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="CURRENT")
public class BookExchange {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @ManyToOne
    @JoinColumn(name = "BOOK_POSTED_ID")
    protected Book bookPostedOnExchange;
    @ManyToOne
    @JoinColumn(name = "EXCHANGE_BOOK_ID")
    protected Book bookOfferedInExchange;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXCHANGE_CHAIN_INCLUDED_IN")
    private BookExchangeChain exchangeChainIncludedIn;

    public Book getBookPostedOnExchange() {
        return bookPostedOnExchange;
    }

    public void setBookPostedOnExchange(Book bookPostedOnExchange) {
        this.bookPostedOnExchange = bookPostedOnExchange;
    }

    public Book getBookOfferedInExchange() {
        return bookOfferedInExchange;
    }

    public void setBookOfferedInExchange(Book bookOfferedInExchange) {
        this.bookOfferedInExchange = bookOfferedInExchange;
    }

    public BookExchangeChain getExchangeChainIncludedIn() {
        return exchangeChainIncludedIn;
    }

    public void setExchangeChainIncludedIn(BookExchangeChain exchangeChainIncludedIn) {
        this.exchangeChainIncludedIn = exchangeChainIncludedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookExchange)) return false;

        BookExchange that = (BookExchange) o;

        if (getBookPostedOnExchange() != null ? !getBookPostedOnExchange().equals(that.getBookPostedOnExchange()) : that.getBookPostedOnExchange() != null)
            return false;
        return !(getBookOfferedInExchange() != null ? !getBookOfferedInExchange().equals(that.getBookOfferedInExchange()) : that.getBookOfferedInExchange() != null);

    }

    @Override
    public int hashCode() {
        int result = getBookPostedOnExchange() != null ? getBookPostedOnExchange().hashCode() : 0;
        result = 31 * result + (getBookOfferedInExchange() != null ? getBookOfferedInExchange().hashCode() : 0);
        return result;
    }
}
