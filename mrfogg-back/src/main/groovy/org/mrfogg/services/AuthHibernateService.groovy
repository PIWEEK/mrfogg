package org.mrfogg.services

import org.mrfogg.domains.User
import com.yammer.dropwizard.auth.AuthenticationException

class AuthHibernateService {
    def userDao

    public User getAuthenticatedUser(String token) {
        def result = userDao.findByToken(token)
        if (!result.isPresent()) {
            throw new AuthenticationException('The user is not authenticated')
        }
        return result.get()
    }

    public String authenticateUser(User user) {
        def authUser = userDao.findByEmail(user.email)
        if (!authUser.isPresent()) {
            throw new AuthenticationException('Invalid user or password')
        }
        return authUser.get().token
    }

    public removeAuth(User user) {
        if (!users[user.token]) {
            throw new AuthenticationException('User not found')
        }
        userDao.removeToken(user)
    }

}
