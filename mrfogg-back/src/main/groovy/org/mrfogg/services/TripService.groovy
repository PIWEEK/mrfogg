package org.mrfogg.services

import org.mrfogg.daos.UserDAO
import org.mrfogg.daos.TripDAO
import org.mrfogg.domains.Trip
import org.mrfogg.domains.User
import org.mrfogg.exceptions.PersistenceException

class TripService {

    UserDAO userDao
    TripDAO tripDao

    List<Trip> listAll() {
        return this.tripDao.listAll()
    }

    Trip get(Long id) {
        return this.tripDao.get(id)
    }

    /**
     * This method saves a new trip adding the creator
     * to the member list
     *
     * @param trip General data about the trip is about to be created
     * @param user The user creating the trip
     *
     * @return the saved trip
     */
    Trip savingTripWithUser(Trip trip, User user) {
        trip.users = [user]
        this.userDao.persist(trip)
    }

    Trip updateTripById(Trip trip, Long id) {
        Trip destination = get(id)
        if (!destination) {
            throw new PersistenceException("No trip found with id $id")
        }
        destination.with {
            name = trip.name
            description = trip.description
        }
        return this.tripDao.persist(destination)
    }

    /**
     * This method adds a user in a trip
     *
     * @userId The user we want to add
     * @tripId The id of the trip
     * @return the updated trip with the new user
     */
    Trip addUserToTrip(Long userId, Long tripId) {

        User user = this.userDao.get(userId)
        Trip trip = this.tripDao.get(tripId)

        if (!user || !trip) {
            return new PersistenceException('To add a user user and trip are needed')
        }

        trip.users << user
        user.trips << trip

        this.userDao.persist(user)
        this.tripDao.persist(trip)

        return trip
    }

}
