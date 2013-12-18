package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.Trip
import org.mrfogg.daos.TripDAO

import com.yammer.metrics.annotation.Timed

@Log4j
@Path('/trips')
@Produces(MediaType.APPLICATION_JSON)
class TripResource {

    TripDAO tripDAO

    @GET
    @Timed
    List<Trip> list() {
        return tripDAO.listAll()
    }

}
