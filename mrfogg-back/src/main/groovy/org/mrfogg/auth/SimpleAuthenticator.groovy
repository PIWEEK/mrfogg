package org.mrfogg.auth

import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.AuthenticationException

import com.google.common.base.Optional

import org.mrfogg.domains.User

class SimpleAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        if ("secret" == token) {
            return Optional.of(new User(email: "alonso.torres@gmail.com"))
        }
        return Optional.absent()
    }
}
