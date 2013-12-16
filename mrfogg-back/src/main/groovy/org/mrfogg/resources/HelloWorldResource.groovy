package org.mrfogg.resources

import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.User

@Path('/hello-world')
@Produces(MediaType.TEXT_PLAIN)
class HelloWorldResource {

    @GET
    @Timed
    String sayHello(@QueryParam('name') Optional<String> name) {
        return "HELLO ${name} WORLD"
    }

    @GET
    @Path("/protected")
    @Timed
    String protectedMethod(@Auth User user) {
        println "Usuario: $user"
        return "HELLO ${user.email} WORLD"
    }
}
