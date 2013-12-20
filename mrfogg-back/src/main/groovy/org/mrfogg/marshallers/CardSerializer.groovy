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

            if (card.comments) {
                writeArrayFieldStart('comments')
                card.comments.each { comment ->
                    writeStartObject()
                    writeObjectFieldStart('user')
                    writeNumberField('id', comment.owner.id)
                    writeStringField('email', comment.owner.email)
                    writeStringField('avatar', comment.owner.avatarURL)
                    writeEndObject()
                    writeStringField('text', comment.text)
                    writeEndObject()
                }
                writeEndArray()
            }

            writeObjectFieldStart('widget')
            if (card.id == 9) {
                writeStringField('template', '/assets/client/mrfogg-widget-videos.html')
                writeStringField('model', 'widgets/videos/' + card.id)
            } else {
                writeStringField('template', '/assets/client/mrfogg-widget-images.html')
                writeStringField('model', 'widgets/images/' + card.id)
            }
            writeEndObject()

            writeEndObject()
        }
    }

}
