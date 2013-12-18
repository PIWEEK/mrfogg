package org.mrfogg.resources

import org.mrfogg.domains.User
import org.mrfogg.daos.UserDAO
import org.mrfogg.domains.Trip
import org.mrfogg.daos.TripDAO
import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth
import com.yammer.dropwizard.hibernate.UnitOfWork

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.User
import groovy.util.logging.Log4j

@Log4j
@Path('/hello-world')
@Produces(MediaType.APPLICATION_JSON)
class HelloWorldResource {

    UserDAO userDAO
    TripDAO tripDAO

    @POST
    @UnitOfWork
    User create(Map params) {
        return this.userDAO.persist(
            new User(params)
        )
    }

    @GET
    @Timed
    User sayHello(@QueryParam('name') Optional<String> name) {
        return new User(email: "${name}@world.com")
    }

    @GET
    @Path('/protected')
    @Timed
    @UnitOfWork
    String protectedMethod(@Auth User user) {
        log.debug "Usuario: $user"
        return "HELLO ${user.email} WORLD"
    }

    @GET
    @Timed
    @UnitOfWork
    @Path('/test')
    Map test() {
        User user = this.userDAO.persist(new User(email: 'mgdelacroix@gmail.com', password: 'aaa'))
        Trip trip = this.tripDAO.persist(new Trip(name: 'London', description: 'hola mundo'))

        if(user.trips) {
            user.trips.add(trip)
        } else {
            user.trips = [trip]
        }
        this.userDAO.persist(user)
        return [message: "conseguido!!"]
    }

    @GET
    @Timed
    @UnitOfWork
    @Path('/user-list')
    Map userList() {
        return [results: this.userDAO.list()]
    }

}
