package org.mrfogg.services

import org.mrfogg.daos.TaskDAO
import org.mrfogg.domains.Task

class TaskService {

    TaskDAO taskDao

    List<Task> listAllByTripId(Long tripId) {
        return this.taskDao.findAllByTripId(tripId)
    }

}