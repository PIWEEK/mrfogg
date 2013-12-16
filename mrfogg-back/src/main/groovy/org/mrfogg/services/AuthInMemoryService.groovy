package org.mrfogg.services

import org.mrfogg.domains.User

class AuthInMemoryService {
    static users = [:]

    public User getAuthenticatedUser(String token) {
        def result = users[token]

        if (!result) {
            throw new Exception("The user is not authenticated")
        }
        return result
    }

    public String authenticateUser(User user) {
        def authUser = users.find { it.value.email == user.email }
        if (authUser) {
            return authUser.key
        }
        def token = "" + UUID.randomUUID()
        user.token = token
        users[token] = user
        return token
    }

    public removeAuth(User user) {
        if (!users[user.token]) {
            throw new Exception("user not found")
        }
        users.remove(user.token)
    }

}
