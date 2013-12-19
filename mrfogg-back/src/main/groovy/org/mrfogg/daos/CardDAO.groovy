package org.mrfogg.daos

import org.mrfogg.domains.Card
import org.hibernate.criterion.Restrictions

import groovy.transform.InheritConstructors

@InheritConstructors
class CardDAO extends BaseDAO<Card> {

    List<Card> findAllByTaskId(Long id) {
        return criteria().createCriteria('task')
                  .add( Restrictions.eq('id', id) )
                  .list()
    }

}
