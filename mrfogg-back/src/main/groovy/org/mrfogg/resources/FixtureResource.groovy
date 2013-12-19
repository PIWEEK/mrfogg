package org.mrfogg.resources

import javax.ws.rs.Produces
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.metrics.annotation.Timed

import org.mrfogg.services.FixtureService

@Path('/fixtures')
@Produces(MediaType.APPLICATION_JSON)
class FixtureResource {

    FixtureService fixtureService

    @GET @Path('populate')
    @Timed
    @UnitOfWork
    Map<String, String> populate() {
        fixtureService.loadInitialData()
        return [message: 'Datos de prueba creados']
    }

}
