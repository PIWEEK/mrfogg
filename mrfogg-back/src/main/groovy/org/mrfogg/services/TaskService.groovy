package org.mrfogg.services

import org.mrfogg.daos.TaskDAO
import org.mrfogg.daos.TripDAO
import org.mrfogg.domains.Task
import org.mrfogg.domains.Trip

class TaskService {

    TaskDAO taskDao
    TripDAO tripDao

    List<Task> listAllByTripId(Long tripId) {
        return this.taskDao.findAllByTripId(tripId)
    }

    Task createTaskForTrip(Long tripId, String name) {
        Trip trip = this.tripDao.get(tripId)

        Task task = this.taskDao.create(new Task(name: name, trip: trip))
        trip.tasks << task
        this.tripDao.persist(trip)

        return task
    }

}