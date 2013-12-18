package org.mrfogg.daos

import org.hibernate.SessionFactory
import com.google.common.base.Optional
import com.yammer.dropwizard.hibernate.AbstractDAO

class BaseDAO<T> extends AbstractDAO<T> {

    SessionFactory factory

    BaseDAO(SessionFactory factory) {
        super(factory)
    }

    Optional<T> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    T create(T o) {
        return persist(o)
    }

    List<T> listAll() {
       list(criteria())
    }

}
