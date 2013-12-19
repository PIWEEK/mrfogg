package org.mrfogg.resources

import groovy.util.logging.Log4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.mrfogg.domains.Task
import org.mrfogg.services.TaskService

import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.hibernate.UnitOfWork

@Log4j
@Path('/trips/{tripId}/tasks')
@Produces(MediaType.APPLICATION_JSON)
class TaskResource {

    TaskService taskService

    @GET
    @Timed
    @UnitOfWork
    List<Task> list(@PathParam('tripId') Long tripId) {
        return taskService.listAllByTripId(tripId)
    }

}