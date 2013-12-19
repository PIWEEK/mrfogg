package org.mrfogg.marshallers

import org.mrfogg.domains.Trip
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.JsonSerializer

class TripSerializer<Trip>  extends JsonSerializer {

    void serialize(Trip trip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.with {
            writeStartObject()
            writeNumberField('id', trip.id)
            writeStringField('name', trip.name)
            writeStringField('description', trip.description)
            writeEndObject()
        }
    }

}
