package org.mrfogg.resources

import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import java.util.concurrent.atomic.AtomicLong

@Path("/hello-world")
@Produces(MediaType.TEXT_PLAIN)
public class HelloWorldResource {
    public HelloWorldResource() {
    }

    @GET
    @Timed
    public String sayHello(@QueryParam("name") Optional<String> name) {
        return "HELLO ${name} WORLD"
    }
}
