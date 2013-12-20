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
import org.mrfogg.services.CardService

import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips/{tripId}/tasks/{taskId}/cards')
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CardResource {

    CardService cardService

    @GET
    @Timed
    @UnitOfWork
    List<Card> card(@PathParam('taskId') Long taskId) {
        List<Card> cards = cardService.listAllByTaskId(taskId)
        cards.comments.each {
            log.info it
        }
        return cards
    }

    @POST
    @Timed
    @UnitOfWork
    Card create(@PathParam('taskId') Long taskId, Map params, @Auth User user) {
        return cardService.createCardForTraskAndUser(taskId, user, params)
    }

    @GET
    @Path('{id}')
    @Timed
    @UnitOfWork
    Card show(@PathParam('id') Long id) {
        return cardService.get(id)
    }

    // update card

}
