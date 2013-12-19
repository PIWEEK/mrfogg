package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.PUT
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.User
import org.mrfogg.domains.Trip
import org.mrfogg.daos.TripDAO
import org.mrfogg.services.TripService

import com.yammer.dropwizard.auth.Auth
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips')
@Produces(MediaType.APPLICATION_JSON)
class TripResource {

    TripService tripService

    @GET
    @Timed
    @UnitOfWork
    List<Trip> list() {
        return tripService.listAll()
    }

    @GET
    @Path('{id}')
    @Timed
    @UnitOfWork
    Trip show(@PathParam('id') Long id) {
        return tripService.get(id)
    }

    @PUT
    @Timed
    @Path('{id}')
    @UnitOfWork
    Trip update(@Auth User user, Trip trip, @PathParam('id') Long id) {
        log.debug("User ${user.email} updating trip")
        return tripService.updateTripById(trip, id)
    }

    @POST
    @Timed
    @UnitOfWork
    Trip create(@Auth User user, Trip trip) {
        return tripService.savingTripWithUser(trip, user)
    }

    @POST
    @Timed
    @Path('{id}/addUser/{userId}')
    @UnitOfWork
    Trip addUserToTrip(@Auth User user,@PathParam('userId') Long addedUser, @PathParam('id') Long trip) {
        log.debug("User ${user.email} is adding user ${addedUser} to trip ${trip}")
        return tripService.addUserToTrip(addedUser, trip)
    }

}
