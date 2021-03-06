package org.mrfogg.services

import org.mrfogg.domains.Card
import org.mrfogg.domains.Comment
import org.mrfogg.domains.User
import org.mrfogg.domains.Trip
import org.mrfogg.domains.Task

@SuppressWarnings('DuplicateNumberLiteral')
class FixtureService {

    Map daoMap

    FixtureService(Map daoMap) {
        this.daoMap = daoMap
    }

    void loadInitialData() {

        User mgdelacroix
        User mariog
        User alotor
        User juan
        User gnufede
        User ramiro

        daoMap.userDAO.with {
            mgdelacroix = create(new User(email: 'mgdelacroix@gmail.com', password: 'mgdelacroix'))
            mariog = create(new User(email: 'mario.ggar@gmail.com', password: 'marioggar'))
            alotor = create(new User(email: 'alotor@gmail.com', password: 'alotor'))
            juan = create(new User(email: 'delacruzgarciajuan@gmail.com', password: 'delacruzgarciajuan'))
            gnufede = create(new User(email: 'gnufede@gmail.com', password: 'gnufede'))
            ramiro = create(new User(email: 'rhbalo1988@gmail.com', password: 'rhbalo1988'))
        }

        Trip londonTrip

        londonTrip = daoMap.tripDAO.create(new Trip(name: 'Londres', description: 'Big Ben and Stuff'))

        londonTrip.users << mgdelacroix
        mgdelacroix.trips << londonTrip
        daoMap.tripDAO.persist(londonTrip)
        daoMap.userDAO.persist(mgdelacroix)

        Task task

        task = daoMap.taskDAO.create(new Task(name: 'Elegir el avión', trip: londonTrip))

        londonTrip.tasks << task
        daoMap.tripDAO.persist(londonTrip)

        3.times { i ->
            Card card = daoMap.cardDAO.create(
                new Card(title: "card ${i + 1}", owner: mgdelacroix, task: task, description: 'desc')
            )
            task.cards << card
            daoMap.taskDAO.persist(task)

            3.times { j ->
                Comment comment = daoMap.commentDAO.create(
                    new Comment(text: "texto de ${j}", owner: mariog, card: card)
                )
                card.comments << comment
                daoMap.cardDAO.persist(card)
            }
        }

    }

}
