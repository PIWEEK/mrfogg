package org.mrfogg.widget.image.resources

import com.google.common.base.Optional
import groovy.util.logging.Log4j

import javax.ws.rs.*
import javax.ws.rs.core.*

import com.yammer.metrics.annotation.Timed


@Log4j
@Path('/widget/image/')
@Produces(MediaType.TEXT_PLAIN)
class ImageWidgetResource {
    @GET
    @Timed
    String test() {
        return "HOLA"
    }
}
