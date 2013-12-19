package org.mrfogg.marshallers

import org.mrfogg.domains.User
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.JsonSerializer

class UserSerializer<User>  extends JsonSerializer {

    void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.with {
            writeStartObject()
            writeNumberField('id', user.id)
            writeStringField('email', user.email)
            writeStringField('avatar', user.avatarURL)
            //writeStringField('token', user.token)
            writeEndObject()
        }
    }

}
