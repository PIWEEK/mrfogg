package org.mrfogg.domains

import javax.persistence.GeneratedValue
import javax.persistence.Id

class BaseDomain {

    @Id
    @GeneratedValue
    Long id

    Date dateCreate
    Date lastUpdate

}
