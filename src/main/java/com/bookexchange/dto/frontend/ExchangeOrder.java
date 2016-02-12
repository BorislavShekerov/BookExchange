package com.bookexchange.dto.frontend;

/**
 * Created by sheke on 11/17/2015.
 */
public class ExchangeOrder {

    private String bookUnderOffer;
    private String bookUnderOfferOwner;

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

}
