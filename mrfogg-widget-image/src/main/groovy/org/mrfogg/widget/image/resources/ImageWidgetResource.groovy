package org.mrfogg.widget.image.resources

import com.google.common.base.Optional
import groovy.util.logging.Log4j

import javax.ws.rs.*
import javax.ws.rs.core.*

import com.yammer.metrics.annotation.Timed


@Log4j
@Path('/widgets/images/')
@Produces(MediaType.APPLICATION_JSON)
class ImageWidgetResource {
    @GET
    @Timed
    @Path("{imageId}")
    List getImage(@PathParam('imageId') Long imageId) {
        return ["http://kaleidos.net/files/images/linux318x260_1.png"]
    }

    @POST
    @Timed
    Map insertImage(Map input) {
        println input
        return input
    }
}
