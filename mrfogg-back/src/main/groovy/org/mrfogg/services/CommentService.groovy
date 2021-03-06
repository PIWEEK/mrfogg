package org.mrfogg.services

import org.mrfogg.daos.CardDAO
import org.mrfogg.daos.CommentDAO
import org.mrfogg.domains.Card
import org.mrfogg.domains.User
import org.mrfogg.domains.Comment

class CommentService {

    CardDAO cardDao
    CommentDAO commentDao

    List<Comment> listAllByCardId(Long id) {
        return this.commentDao.findAllByCardId(id)
    }

    Comment get(Long id) {
        return this.commentDao.get(id)
    }

    Comment createCommentForCardAndUser(Long cardId, User user, Map params) {
        Card card = cardDao.get(cardId)

        Comment comment = commentDao.create(new Comment(text: params.text, owner: user, card: card))

        card.comments << comment
        cardDao.persist(card)

        return comment
    }

}