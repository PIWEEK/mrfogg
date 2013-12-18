package org.mrfogg.domains

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.ManyToMany
import javax.persistence.JoinTable
import javax.persistence.FetchType

@Entity
@Table(name = 'trip')
class Trip {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String name
    String description

    @ManyToMany(targetEntity = User, fetch = FetchType.EAGER)
    @JoinTable
    List<User> users

}
