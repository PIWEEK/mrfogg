package org.mrfogg.domains

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'user')
class User {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String username
    String password

}