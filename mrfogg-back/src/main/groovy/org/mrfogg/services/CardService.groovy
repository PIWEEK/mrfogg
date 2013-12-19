package org.mrfogg.services

import org.mrfogg.daos.CardDAO
import org.mrfogg.domains.Card

class CardService {

    CardDAO cardDao

    List<Card> listAllByTaskId(Long id) {
        return this.cardDao.findAllByTaskId(id)
    }

}