package org.mrfogg.daos

import org.mrfogg.domains.Task
import org.hibernate.criterion.Restrictions

import groovy.transform.InheritConstructors

@InheritConstructors
class TaskDAO extends BaseDAO<Task> {

    List<Task> findAllByTripId(Long tripId) {
        return criteria().createCriteria('trip')
                  .add( Restrictions.eq('id', tripId) )
                  .list()
    }

}
