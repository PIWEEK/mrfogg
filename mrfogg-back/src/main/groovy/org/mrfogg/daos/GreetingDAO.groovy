package org.mrfogg.daos

import org.mrfogg.domains.Greeting
import com.google.common.base.Optional
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class GreetingDAO extends AbstractDAO<Greeting> {

    GreetingDAO(SessionFactory factory) {
        super(factory)
    }

    Optional<Greeting> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    Greeting create(Greeting greeting) {
        return persist(greeting)
    }

}
