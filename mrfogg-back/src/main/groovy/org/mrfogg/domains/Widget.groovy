package org.mrfogg.domains

import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.Column

@Entity
@Table(name = 'card_widget')
class Widget extends BaseDomain {

    @Column(unique = true)
    Long cardId

    @Column(nullable = false)
    String model
    @Column(nullable = false)
    String template

}
