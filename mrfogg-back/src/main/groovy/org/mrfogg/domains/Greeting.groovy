package org.mrfogg.domains

import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
@Table(name = 'greeting')
class Greeting {

    @Id
    @GeneratedValue
    Long id

    String message

}
