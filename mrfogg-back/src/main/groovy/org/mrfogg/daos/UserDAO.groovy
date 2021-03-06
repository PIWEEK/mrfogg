package org.mrfogg.daos

import static org.apache.commons.codec.digest.DigestUtils.shaHex

import org.mrfogg.domains.User
import com.google.common.base.Optional

import org.hibernate.HibernateException
import org.hibernate.criterion.Restrictions

import groovy.transform.InheritConstructors

@InheritConstructors
class UserDAO extends BaseDAO<User> {

    Optional<User> findByEmail(String email) throws HibernateException{
        _findByAttribute('email', email)
    }

    Optional<User> findByToken(String token) throws HibernateException{
        _findByAttribute('token', token)
    }

    Optional<User> _findByAttribute(String attribute, String value) throws HibernateException{
        def criteria = currentSession().createCriteria(User)
        criteria.add(Restrictions.eq(attribute, value))
        return Optional.fromNullable(criteria.uniqueResult())
    }

    User create(User user) {
        user.password = crypt(user.password)
        user.token = "${UUID.randomUUID()}"
        super.create(user)
    }

    void removeToken(User user) {
        user.token = null
        persist(user)
    }

    String crypt(String password) {
        return shaHex(password.getBytes('UTF-8'))
    }

    List<User> list() {
        list(criteria())
    }

}
