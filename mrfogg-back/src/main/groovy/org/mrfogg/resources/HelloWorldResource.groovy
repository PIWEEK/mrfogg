package org.mrfogg.resources

import org.mrfogg.domains.Greeting
import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path('/hello-world')
@Produces(MediaType.APPLICATION_JSON)
class HelloWorldResource {

    @GET
    @Timed
    Greeting sayHello(@QueryParam('name') Optional<String> name) {
        return new Greeting(message: "HELLO ${name} WORLD")
    }
}
