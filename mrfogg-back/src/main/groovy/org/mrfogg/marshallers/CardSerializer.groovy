package org.mrfogg.marshallers

import org.mrfogg.domains.Card
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.JsonSerializer

class CardSerializer<Card> extends JsonSerializer {

    void serialize(Card card, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.with {
            writeStartObject()
            writeNumberField('id', card.id)
            writeStringField('title', card.title)
            writeStringField('description', card.description)
            writeObjectFieldStart('owner')
            writeNumberField('id', card.owner.id)
            writeStringField('email', card.owner.email)
            writeStringField('avatar', card.owner.avatarURL)
            writeEndObject()

            writeObjectFieldStart('widget')
            //writeStringField('template', "/assets/client/mrfogg-widget-images.html")
            //writeStringField('model', "widgets/images/2")
            writeStringField('template', "/assets/client/mrfogg-widget-videos.html")
            writeStringField('model', "widgets/videos/2")
            writeEndObject()

            writeEndObject()
        }
    }

}
