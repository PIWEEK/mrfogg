package org.mrfogg.daos

import org.mrfogg.domains.Trip
import com.google.common.base.Optional

import org.hibernate.SessionFactory

import com.yammer.dropwizard.hibernate.AbstractDAO

class TripDAO extends AbstractDAO<Trip> {

    TripDAO(SessionFactory factory) {
        super(factory)
    }

    Optional<Trip> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    Trip create(Trip trip) {
        return persist(trip)
    }

}
