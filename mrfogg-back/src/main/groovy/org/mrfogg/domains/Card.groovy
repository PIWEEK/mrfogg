package org.mrfogg.domains

import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Table(name = 'task_card')
class Card extends BaseDomain {

    @Column(unique = true, nullable = false)
    String title
    @Column(nullable = false)
    User owner

    @ManyToOne
    @JoinColumn
    Task task

    Widget widget

}
