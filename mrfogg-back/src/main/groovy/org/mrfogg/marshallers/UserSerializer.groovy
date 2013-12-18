package org.mrfogg.marshallers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException

import com.fasterxml.jackson.databind.JsonSerializer

class UserSerializer extends JsonSerializer {

    void serialize(Object user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.with {
            writeStartObject()
            writeNumberField("id", user.id)
            writeStringField("email", user.email)
            writeStringField("avatar", user.avatarURL)
            //writeStringField("token", user.token)
            writeEndObject()
        }
    }

}