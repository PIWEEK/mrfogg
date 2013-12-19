package org.mrfogg.domains

import javax.persistence.GeneratedValue
import javax.persistence.MappedSuperclass
import javax.persistence.Id

@MappedSuperclass
class BaseDomain {

    @Id
    @GeneratedValue
    Long id

    Date dateCreate
    Date lastUpdate

}
