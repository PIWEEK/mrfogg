package org.mrfogg.auth

import groovy.util.logging.Log4j
import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.AuthenticationException
import com.google.common.base.Optional
import org.mrfogg.domains.User


@Log4j
class TokenAuthenticator implements Authenticator<String, User> {

    def authService

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {

        try {

            return Optional.of(authService.getAuthenticatedUser(token))

        } catch (e) {
            log.error e.message
            return Optional.absent()
        }
    }
}
