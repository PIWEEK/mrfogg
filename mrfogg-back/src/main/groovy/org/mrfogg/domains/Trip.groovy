package org.mrfogg.domains

import static javax.persistence.CascadeType.ALL

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.OneToMany
import javax.persistence.ManyToMany
import javax.persistence.JoinTable
import javax.persistence.FetchType

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.mrfogg.marshallers.TripSerializer

@Entity
@Table(name = 'trip')
@JsonSerialize(using = TripSerializer)
class Trip extends BaseDomain {

    @Column(unique = true, nullable = false)
    String name
    @Column(nullable = false)
    String description

    @JoinTable
    @ManyToMany(targetEntity = User, fetch = FetchType.EAGER)
    List<User> users = []

    @OneToMany(cascade = ALL, mappedBy = 'trip')
    List<Task> tasks = []

}
