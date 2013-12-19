package org.mrfogg.services

import org.mrfogg.domains.User

class FixtureService {

    Map daoMap

    FixtureService(Map daoMap) {
        this.daoMap = daoMap
    }

    void loadInitialData() {

        daoMap.userDAO.with {
            create(new User(email: 'mgdelacroix@gmail.com', password: 'mgdelacroix'))
            create(new User(email: 'mario.ggar@gmail.com', password: 'marioggar'))
            create(new User(email: 'alotor@gmail.com', password: 'alotor'))
        }

    }

}