package com.bookexchange.dto;

/**
 * Created by sheke on 1/29/2016.
 */
public class SimpleExchangeOrder extends ExchangeOrder {
    private String bookOfferedInExchange;
    private String bookOfferedInExchangeOwner;

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
