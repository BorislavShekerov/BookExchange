package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by sheke on 11/14/2015.
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule("ObjectIdmodule");
        module.addSerializer(Book.class, new BookJSONSerializer());
        this.registerModule(module);
    }

}
