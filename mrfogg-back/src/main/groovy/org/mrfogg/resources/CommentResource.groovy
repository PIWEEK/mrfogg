package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.Card
import org.mrfogg.domains.Comment
import org.mrfogg.domains.User
import org.mrfogg.services.CommentService

import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips/{tripId}/tasks/{taskId}/cards/{cardId}/comments')
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CommentResource {

    CommentService commentService

    @POST
    @Timed
    @UnitOfWork
    Comment create(@PathParam('cardId') Long cardId, Map params, @Auth User user) {
        return commentService.createCommentForCardAndUser(cardId, user, params)
    }

}