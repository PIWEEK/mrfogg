package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.Task
import org.mrfogg.services.TaskService

import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips/{tripId}/tasks')
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class TaskResource {

    TaskService taskService

    @GET
    @Timed
    @UnitOfWork
    List<Task> list(@PathParam('tripId') Long tripId) {
        return taskService.listAllByTripId(tripId)
    }

    @POST
    @Timed
    @UnitOfWork
    Task create(@PathParam('tripId') Long tripId, Map params) {
        return taskService.createTaskForTrip(tripId, params.name)
    }

}