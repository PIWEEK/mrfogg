package org.mrfogg.exceptions

import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ExceptionMapper

import com.yammer.metrics.annotation.Timed

@Provider
class ThrowableMapper implements ExceptionMapper<Throwable> {

    @Timed
    Response toResponse(Throwable exception) {
        return Response.
            status(Response.Status.INTERNAL_SERVER_ERROR).
            entity(exception).
            type(MediaType.APPLICATION_JSON).
        build()
    }

}
