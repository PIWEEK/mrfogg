package org.mrfogg.services

import org.mrfogg.daos.TaskDAO
import org.mrfogg.daos.TripDAO
import org.mrfogg.domains.Task
import org.mrfogg.domains.Trip
import org.mrfogg.exceptions.PersistenceException

class TaskService {

    TaskDAO taskDao
    TripDAO tripDao

    List<Task> listAllByTripId(Long id) {
        def trip = this.tripDao.get(id)
        def result = null

        if (trip) {
            result = trip.tasks
            println result*.id
        }
        return result
    }

    Task get(Long id) {
        return this.taskDao.get(id)
    }

    Task createTaskForTrip(Long id, String name) {
        Trip trip = this.tripDao.get(id)

        Task task = this.taskDao.create(new Task(name: name, trip: trip))
        trip.tasks << task
        this.tripDao.persist(trip)

        return task
    }

    Task updateTaskById(Task task, Long id) {
        Task destination = get(id)

        if (!destination) {
            throw new PersistenceException("No task found with id $id")
        }

        destination.with {
            name = task.name
            status = task.status
        }
        return this.taskDao.persist(destination)
    }

}
