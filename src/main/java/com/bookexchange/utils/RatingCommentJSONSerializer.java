package com.bookexchange.utils;

import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.RatingComment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by sheke on 2/20/2016.
 */
public class RatingCommentJSONSerializer extends JsonSerializer<RatingComment> {

    @Override
    public void serialize(RatingComment ratingComment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("comment",ratingComment.getCommentMessage());
        jsonGenerator.writeNumberField("rating", ratingComment.getRating());
        jsonGenerator.writeStringField("commentatorName",ratingComment.getCommentFrom().getFullName());
        jsonGenerator.writeStringField("commentForEmail",ratingComment.getCommentFor().getEmail());
        jsonGenerator.writeStringField("commentatorEmail",ratingComment.getCommentFrom().getEmail());
        jsonGenerator.writeStringField("commentatorAvatar",ratingComment.getCommentFrom().getAvatarUrl());

        jsonGenerator.writeEndObject();
    }
}
