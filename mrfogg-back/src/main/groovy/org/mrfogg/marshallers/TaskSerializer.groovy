package org.mrfogg.marshallers

import org.mrfogg.domains.Task
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.JsonSerializer

class TaskSerializer<Task> extends JsonSerializer {

    void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.with {
            writeStartObject()
            writeNumberField('id', task.id)
            writeStringField('name', task.name)
            writeStringField('status', task.status.toString())
            writeEndObject()
        }
    }

}