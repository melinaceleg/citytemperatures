package com.candoit.weather.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class WeatherDeserializer implements JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
       return jsonElement.getAsJsonObject().get("temp").getAsDouble();
    }
}
