package org.mrfogg.resources

import com.google.common.base.Optional
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.auth.Auth

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import org.mrfogg.domains.User

@Path('/')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthResource {
    def authService

    @POST
    @Path('/login')
    def login(Map input) {
        def user = new User(email:input.email, password:input.password, avatar: "")
        def token = authService.authenticateUser(user)
        return ["token": token]
    }

    @POST
    @Path('/logout')
    Response logout(@Auth User user) {
        authService.removeAuth(user)
        return Response.noContent().build()
    }
}
