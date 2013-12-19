package org.mrfogg.services

import org.mrfogg.daos.CardDAO
import org.mrfogg.domains.Card
import org.mrfogg.domains.User
import org.mrfogg.domains.Task

class CardService {

    CardDAO cardDao

    List<Card> listAllByTaskId(Long id) {
        return this.cardDao.findAllByTaskId(id)
    }

    Card get(Long id) {
        return this.cardDao.get(id)
    }

    Card createCardForTraskAndUser(Long taskId, User user, Map params) {
        Task task = get(taskId)

        return cardDao.create(new Card(title: params.title, owner: user, task: task))
    }

}