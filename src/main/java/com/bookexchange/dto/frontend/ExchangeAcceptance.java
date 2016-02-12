package com.bookexchange.dto.frontend;

/**
 * Created by sheke on 2/5/2016.
 */
public class ExchangeAcceptance {
    private int bookId;
    private int exchangeId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }
}
