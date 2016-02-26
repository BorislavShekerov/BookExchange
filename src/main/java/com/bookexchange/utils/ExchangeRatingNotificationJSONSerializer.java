package com.bookexchange.utils;

import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.ExchangeRatingNotification;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by sheke on 2/26/2016.
 */
public class ExchangeRatingNotificationJSONSerializer extends JsonSerializer<ExchangeRatingNotification> {
    @Override
    public void serialize(ExchangeRatingNotification exchangeRatingNotification, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("userRatedEmail",exchangeRatingNotification.getUserRated().getEmail());
        jsonGenerator.writeStringField("userRatedFullName", exchangeRatingNotification.getUserRated().getFullName());
        jsonGenerator.writeStringField("message", exchangeRatingNotification.getMessage());
        jsonGenerator.writeObjectField("notificationType", exchangeRatingNotification.getNotificationType());
        jsonGenerator.writeNumberField("id",exchangeRatingNotification.getId());

        jsonGenerator.writeEndObject();
    }
}
