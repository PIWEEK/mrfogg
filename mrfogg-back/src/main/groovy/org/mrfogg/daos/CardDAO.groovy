package org.mrfogg.daos

import org.mrfogg.domains.Card
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Order

import groovy.transform.InheritConstructors

@InheritConstructors
class CardDAO extends BaseDAO<Card> {

    List<Card> findAllByTaskId(Long id) {
        return criteria()
                  .add( Restrictions.eq('task.id', id) )
                  .addOrder(Order.desc('id'))
                  .list()
    }

}
