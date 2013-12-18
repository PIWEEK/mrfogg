package org.mrfogg.domains

import static javax.persistence.CascadeType.ALL

import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.OneToMany
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

@Entity
@Table(name = 'trip_task')
class Task extends BaseDomain {

    @Column(unique = true, nullable = false)
    String name

    @ManyToOne
    @JoinColumn
    Trip trip

    @OneToMany(cascade = ALL, mappedBy = 'task')
    List<Card> cards

}

