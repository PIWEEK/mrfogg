package org.mrfogg.domains

import static javax.persistence.CascadeType.ALL

import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.OneToMany

import com.fasterxml.jackson.databind.annotation.JsonSerialize

import org.mrfogg.marshallers.CardSerializer

@Entity
@Table(name = 'task_card')
@JsonSerialize(using = CardSerializer)
class Card extends BaseDomain {

    @Column(nullable = false)
    String title

    @Column(nullable = false)
    String description

    @ManyToOne
    @JoinColumn
    User owner

    @ManyToOne
    @JoinColumn
    Task task

    @OneToOne
    Widget widget

    @OneToMany(cascade = ALL)
    List<Comment> comments = []

}