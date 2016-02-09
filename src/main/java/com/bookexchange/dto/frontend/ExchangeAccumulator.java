package com.bookexchange.dto.frontend;

import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.utils.DateComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheke on 2/8/2016.
 */
public class ExchangeAccumulator {
    private List<DirectBookExchange> directExchanges;
    private List<BookExchangeChain> bookExchangeChains;

    public ExchangeAccumulator(List<DirectBookExchange> directBookExchangeLIst,List<BookExchangeChain> bookExchangeChains){
        this.directExchanges = directBookExchangeLIst;
        this.bookExchangeChains = bookExchangeChains;
    }

    public ExchangeAccumulator sortByCreationDate(){
        directExchanges.stream().sorted((exchange1,exchange2) -> new DateComparator().compare(exchange1.getDateCreated(),exchange2.getDateCreated()));
        bookExchangeChains.stream().sorted((exchangeChain1,exchangeChain2) -> new DateComparator().compare(exchangeChain1.getDateCreated(),exchangeChain2.getDateCreated()));

        return this;
    }

    public List<DirectBookExchange> getDirectExchanges() {
        return directExchanges;
    }

    public void setDirectExchanges(List<DirectBookExchange> directExchanges) {
        this.directExchanges = directExchanges;
    }

    public List<BookExchangeChain> getBookExchangeChains() {
        return bookExchangeChains;
    }

    public void setBookExchangeChains(List<BookExchangeChain> bookExchangeChains) {
        this.bookExchangeChains = bookExchangeChains;
    }
}
