package org.mrfogg

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.auth.oauth.OAuthProvider

import org.mrfogg.domains.User
import org.mrfogg.daos.UserDAO
import org.mrfogg.services.AuthInMemoryService
import org.mrfogg.resources.HelloWorldResource
import org.mrfogg.resources.AuthResource
import org.mrfogg.auth.TokenAuthenticator
import org.mrfogg.domains.User

class MrFoggService extends Service<MrFoggConfiguration> {

    static final Class[] ENTITIES = [
        org.mrfogg.domains.User
    ]

    public static void main(String[] args) throws Exception {
        new MrFoggService().run(args)
    }

    HibernateBundle<MrFoggConfiguration> hibernateBundle =

        new HibernateBundle<MrFoggConfiguration>(ENTITIES) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(MrFoggConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    MigrationsBundle<MrFoggConfiguration> migrationsBundle =
        new MigrationsBundle<MrFoggConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(MrFoggConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<MrFoggConfiguration> bootstrap) {
        bootstrap.with {
            name = 'MrFogg'
            addBundle migrationsBundle
            addBundle hibernateBundle
        }
    }

    @Override
    public void run(MrFoggConfiguration configuration, Environment environment) throws ClassNotFoundException {
        def authService = new AuthInMemoryService()

        final UserDAO userDAO = new UserDAO(hibernateBundle.sessionFactory)
        environment.addResource(new HelloWorldResource(userDAO))
        environment.addResource(new AuthResource(authService:authService))
        environment.addResource(new OAuthProvider<User>(new TokenAuthenticator(authService:authService), 'MR.FOGG'))
    }
}
