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
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType

import groovy.util.logging.Log4j

@Log4j
@Path('/users')
@Produces(MediaType.APPLICATION_JSON)
class UserResource {

    UserDAO userDAO

    @POST
    @UnitOfWork
    User create(Map params) {
        return this.userDAO.persist(
            new User(params)
        )
    }

    @GET
    @Timed
    @UnitOfWork
    List<User> list() {
        return this.userDAO.list()
    }

    @GET @Path("{id}")
    @Timed
    @UnitOfWork
    User show(@PathParam('id') Long id) {
        return this.userDAO.findById(id).get()
    }

    @GET @Path('me')
    @Timed
    @UnitOfWork
    User me(@Auth User user) {
        return user
    }

    @GET
    @Timed
    @UnitOfWork
    @Path('/populate')
    Map test() {
        this.userDAO.with {
            persist(new User(email: 'mgdelacroix@gmail.com', password: 'mgdelacroix'))
            persist(new User(email: 'mario.ggar@gmail.com', password: 'marioggar'))
            persist(new User(email: 'alotor@gmail.com', password: 'alotor'))
        }
        
        return [message: "Usuarios creados"]
    }

}
