package org.mrfogg.domains

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.ManyToMany

import com.fasterxml.jackson.databind.annotation.JsonSerialize

import static org.apache.commons.codec.digest.DigestUtils.md5Hex

import org.mrfogg.marshallers.UserSerializer

@Entity
@Table(name = 'user')
@JsonSerialize(using = UserSerializer)
class User {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String email
    String password
    String token
    @ManyToMany(targetEntity = Trip)
    List<Trip> trips

    /**
     * Obtains the Gravatar url for the user avatar
     * This url is obtained from a HASH of the email trimmed and in lowercase
     */
    String getAvatarURL() {
        String url = 'http://www.gravatar.com/avatar/'
        String hashedEmail = md5Hex(
            this.email.trim()
                      .toLowerCase()
                      .getBytes('UTF-8')
        )
        return url + hashedEmail
    }

}
