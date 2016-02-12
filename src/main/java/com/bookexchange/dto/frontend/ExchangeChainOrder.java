package com.bookexchange.dto.frontend;

import com.bookexchange.dto.User;

import java.util.List;

/**
 * Created by sheke on 2/9/2016.
 */
public class ExchangeChainOrder {
    List<String> userChain;
    String bookRequestedTitle;

    public List<String> getUserChain() {
        return userChain;
    }

    public void setUserChain(List<String> userChain) {
        this.userChain = userChain;
    }

    public String getBookRequestedTitle() {
        return bookRequestedTitle;
    }

    public void setBookRequestedTitle(String bookRequestedTitle) {
        this.bookRequestedTitle = bookRequestedTitle;
    }
}
