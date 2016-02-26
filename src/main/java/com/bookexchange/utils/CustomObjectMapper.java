package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.RatingComment;
import com.bookexchange.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Created by sheke on 11/14/2015.
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule("ObjectIdmodule");
        module.addSerializer(Book.class, new BookJSONSerializer());
        module.addSerializer(DirectBookExchange.class,new DirectBookExchangeJSONSerializer());
        module.addSerializer(RatingComment.class,new RatingCommentJSONSerializer());
        this.registerModule(module);
        this.registerModule(new JodaModule());
    }

}
