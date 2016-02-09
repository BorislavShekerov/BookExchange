package com.bookexchange.dto.frontend;

/**
 * Created by sheke on 2/5/2016.
 */
public class ExchangeChainAcceptance {
    private int bookId;
    private int exchangeChainId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getExchangeChainId() {
        return exchangeChainId;
    }

    public void setExchangeChainId(int exchangeChainId) {
        this.exchangeChainId = exchangeChainId;
    }
}
