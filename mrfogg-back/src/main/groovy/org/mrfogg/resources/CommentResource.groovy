package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.GET
import javax.ws.rs.POST
//import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.Card
import org.mrfogg.domains.User
import org.mrfogg.services.CommentService

import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips/{tripId}/tasks/{taskId}/cards/comments')
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CommentResource {

    CommentService commentService

    /*
    @POST
    @
    */

}