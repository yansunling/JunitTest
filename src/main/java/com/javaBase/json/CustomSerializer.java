package com.javaBase.json;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomSerializer  extends JsonSerializer<String> {
    @Override
    public void serialize(String user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(user+"_test");
        jsonGenerator.writeStringField("username", user);
    }
}
