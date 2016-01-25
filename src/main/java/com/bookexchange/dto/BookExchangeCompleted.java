package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sheke on 11/13/2015.
 */
@Entity
@Table(name = "BOOK_EXCHANGES")
@DiscriminatorValue(value="COMPLETED")
public class BookExchangeCompleted extends BookExchange{
    @Column(name = "EXCHANGE_SUCCESS")
    private boolean exchangeSuccess;

    public boolean isExchangeSuccess() {
        return exchangeSuccess;
    }

    public void setExchangeSuccess(boolean exchangeSuccess) {
        this.exchangeSuccess = exchangeSuccess;
    }
}
