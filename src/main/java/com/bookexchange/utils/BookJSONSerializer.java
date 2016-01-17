package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by sheke on 11/14/2015.
 */
public class BookJSONSerializer extends JsonSerializer<Book> {


    @Override
    public void serialize(Book book, JsonGenerator jsonGen,
                          SerializerProvider provider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("title", book.getTitle());
        jsonGen.writeStringField("imgUrl", book.getImgUrl());
        jsonGen.writeObjectField("datePosted", book.getDatePosted());
        jsonGen.writeStringField("category",book.getCategory().getCategoryName());

        addOwnerData(book.getPostedBy(), jsonGen);

        jsonGen.writeEndObject();
    }

    private void addOwnerData(User postedBy, JsonGenerator jsonGen) throws IOException {
        jsonGen.writeStringField("ownerFirstname", postedBy.getFirstName());
        jsonGen.writeStringField("ownerLastname", postedBy.getLastName());
        jsonGen.writeStringField("ownerEmail", postedBy.getEmail());
        jsonGen.writeStringField("ownerAvatar",postedBy.getAvatarUrl());

        jsonGen.writeFieldName("ownerCategoriesOfInterest");
        jsonGen.writeStartArray();
        for (BookCategory bookCategory : postedBy.getCategoriesInterestedIn()) {
            jsonGen.writeString(bookCategory.getCategoryName());
        }

        jsonGen.writeEndArray();
    }
}
