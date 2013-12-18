package org.mrfogg.domains

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.ManyToMany
import javax.persistence.FetchType

import com.fasterxml.jackson.databind.annotation.JsonSerialize

import static org.apache.commons.codec.digest.DigestUtils.md5Hex

import org.mrfogg.marshallers.UserSerializer

@Entity
@Table(name = 'user')
@JsonSerialize(using = UserSerializer.class)
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

}