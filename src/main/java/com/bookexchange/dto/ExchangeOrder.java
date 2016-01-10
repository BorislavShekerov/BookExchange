package com.bookexchange.dto;

/**
 * Created by sheke on 11/17/2015.
 */
public class ExchangeOrder {

    private String bookUnderOffer;
    private String bookUnderOfferOwner;
    private String bookOfferedInExchange;
    private String bookOfferedInExchangeOwner;

    public String getBookUnderOffer() {
        return bookUnderOffer;
    }

    public void setBookUnderOffer(String bookUnderOffer) {
        this.bookUnderOffer = bookUnderOffer;
    }

    public String getBookUnderOfferOwner() {
        return bookUnderOfferOwner;
    }

    public void setBookUnderOfferOwner(String bookUnderOfferOwner) {
        this.bookUnderOfferOwner = bookUnderOfferOwner;
    }

    public String getBookOfferedInExchange() {
        return bookOfferedInExchange;
    }

    public void setBookOfferedInExchange(String bookOfferedInExchange) {
        this.bookOfferedInExchange = bookOfferedInExchange;
    }

    public String getBookOfferedInExchangeOwner() {
        return bookOfferedInExchangeOwner;
    }

    public void setBookOfferedInExchangeOwner(String bookOfferedInExchangeOwner) {
        this.bookOfferedInExchangeOwner = bookOfferedInExchangeOwner;
    }
}
