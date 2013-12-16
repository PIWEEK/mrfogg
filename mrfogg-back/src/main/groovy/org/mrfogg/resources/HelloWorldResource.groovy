package org.mrfogg.resources

import org.mrfogg.domains.Greeting
import org.mrfogg.daos.GreetingDAO
import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.hibernate.UnitOfWork

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path('/hello-world')
@Produces(MediaType.APPLICATION_JSON)
class HelloWorldResource {

    GreetingDAO dao

    HelloWorldResource(GreetingDAO dao) {
        this.dao = dao
    }

    @POST
    @UnitOfWork
    Greeting create(String greeting) {
        return this.dao.persist(
            new Greeting(message: greeting)
        )
    }

    @GET
    @Timed
    Greeting sayHello(@QueryParam('name') Optional<String> name) {
        return new Greeting(message: "HELLO ${name} WORLD")
    }

}
