package org.mrfogg.daos

import static com.google.common.base.Preconditions.checkNotNull

import org.mrfogg.domains.Trip
import com.google.common.base.Optional

import org.hibernate.SessionFactory
import org.hibernate.HibernateException

import com.yammer.dropwizard.hibernate.AbstractDAO
import com.yammer.dropwizard.hibernate.UnitOfWork

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