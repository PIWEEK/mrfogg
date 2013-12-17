package org.mrfogg.domains

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.ManyToMany
import javax.persistence.FetchType

@Entity
@Table(name = 'user')
class User {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String email
    String password
    String token
    @ManyToMany(targetEntity = Trip, fetch = FetchType.EAGER)
    List<Trip> trips

}