package org.mrfogg.widget.image.resources

import com.google.common.base.Optional
import groovy.util.logging.Log4j

import javax.ws.rs.*
import javax.ws.rs.core.*

import com.yammer.metrics.annotation.Timed


@Log4j
@Path('/widgets/videos/')
@Produces(MediaType.APPLICATION_JSON)
class VideoWidgetResource {
    @GET
    @Timed
    @Path("{videoId}")
    String getVideo(@PathParam('imageId') Long imageId) {
        return "http://www.youtube.com/watch?v=syxGGxT7_2Y"
    }

    @POST
    @Timed
    Map insertVideo(Map input) {
        println input
        return input
    }
}
