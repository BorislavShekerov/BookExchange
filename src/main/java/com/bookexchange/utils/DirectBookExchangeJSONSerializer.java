package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by sheke on 2/11/2016.
 */
public class DirectBookExchangeJSONSerializer extends JsonSerializer<DirectBookExchange> {


    @Override
    public void serialize(DirectBookExchange directBookExchange, JsonGenerator jsonGen,
                          SerializerProvider provider) throws IOException {
        jsonGen.writeStartObject();

        jsonGen.writeObjectField("bookRequested",directBookExchange.getBookRequested());
        jsonGen.writeObjectField("bookOfferedInExchange", directBookExchange.getBookOfferedInExchange());
        jsonGen.writeBooleanField("successful",directBookExchange.isSuccessful());
        jsonGen.writeBooleanField("over",directBookExchange.isOver());
        jsonGen.writeObjectField("exchangeInitiator",directBookExchange.getExchangeInitiator());
        jsonGen.writeNumberField("id",directBookExchange.getId());
        jsonGen.writeEndObject();
    }
}
