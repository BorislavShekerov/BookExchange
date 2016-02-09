package com.bookexchange.dto;

import javax.persistence.*;

/**
 * Created by sheke on 11/13/2015.
 */
@Entity
@Table(name = "BOOK_EXCHANGES")
@DiscriminatorValue(value="COMPLETED")
public class DirectBookExchangeCompleted extends DirectBookExchange {
    @Column(name = "EXCHANGE_SUCCESS")
    private boolean exchangeSuccess;

    public boolean isExchangeSuccess() {
        return exchangeSuccess;
    }

    public void setExchangeSuccess(boolean exchangeSuccess) {
        this.exchangeSuccess = exchangeSuccess;
    }
}