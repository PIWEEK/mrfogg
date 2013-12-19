package org.mrfogg.resources

import org.mrfogg.domains.User
import org.mrfogg.daos.UserDAO
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

    @GET
    @Timed
    @UnitOfWork
    List<User> list() {
        return this.userDAO.list()
    }

    @GET @Path('{id}')
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

    @GET @Path('/populate')
    @Timed
    @UnitOfWork
    Map test() {
        this.userDAO.with {
            create(new User(email: 'mgdelacroix@gmail.com', password: 'mgdelacroix'))
            create(new User(email: 'mario.ggar@gmail.com', password: 'marioggar'))
            create(new User(email: 'alotor@gmail.com', password: 'alotor'))
        }
        return [message: 'Usuarios creados']
    }

}
