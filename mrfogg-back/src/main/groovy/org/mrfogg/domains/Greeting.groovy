package org.mrfogg.domains

import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
class Greeting {

    @Id
    @GeneratedValue
    Long id

    String message

}
