package org.mrfogg.domains

import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Table(name = 'comment')
class Comment extends BaseDomain {

    @Column(nullable = false)
    String text

    @ManyToOne
    @JoinColumn
    User owner

    @ManyToOne
    @JoinColumn
    Card card

}