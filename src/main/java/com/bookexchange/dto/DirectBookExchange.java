package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

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
public class DirectBookExchange {
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
    @Column(name = "DATE_POSTED")
    private LocalDateTime dateCreated;

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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectBookExchange)) return false;

        DirectBookExchange that = (DirectBookExchange) o;

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


    public static class BookExchangeBuilder{
        private DirectBookExchange directBookExchange;

        public BookExchangeBuilder(){
            directBookExchange = new DirectBookExchange();
        }

        public BookExchangeBuilder setBookPostedOnExchange(Book bookPostedOnExchange) {
            directBookExchange.setBookPostedOnExchange(bookPostedOnExchange); return this;
        }


        public BookExchangeBuilder setBookOfferedInExchange(Book bookOfferedInExchange) {
            directBookExchange.setBookOfferedInExchange(bookOfferedInExchange); return this;
        }

        public BookExchangeBuilder setDateCreated(LocalDateTime dateCreated) {
            directBookExchange.setDateCreated(dateCreated); return this;
        }


        public DirectBookExchange buildBookExchange(){
            return directBookExchange;
        }
    }

}
