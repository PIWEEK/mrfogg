package org.mrfogg.daos

import static com.google.common.base.Preconditions.checkNotNull

import static org.apache.commons.codec.digest.DigestUtils.shaHex

import org.mrfogg.domains.User
import com.google.common.base.Optional

import org.hibernate.SessionFactory
import org.hibernate.HibernateException

import com.yammer.dropwizard.hibernate.AbstractDAO
import com.yammer.dropwizard.hibernate.UnitOfWork

class UserDAO extends AbstractDAO<User> {

    UserDAO(SessionFactory factory) {
        super(factory)
    }

    Optional<User> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    User create(User user) {
        return persist(user)
    }

    @UnitOfWork
    User persist(User user) throws HibernateException {
        user.password = crypt(user.password)
        currentSession().saveOrUpdate(checkNotNull(user))
        return user
    }

    String crypt(String password) {
        return shaHex(password.getBytes("UTF-8"))
    }

}