package org.mrfogg

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.auth.oauth.OAuthProvider
import com.yammer.dropwizard.cli.Cli
import com.yammer.dropwizard.cli.ServerCommand
import com.yammer.dropwizard.config.Bootstrap

import org.mrfogg.auth.TokenAuthenticator
import org.mrfogg.daos.TripDAO
import org.mrfogg.daos.UserDAO
import org.mrfogg.domains.Trip
import org.mrfogg.domains.User
import org.mrfogg.resources.AuthResource
import org.mrfogg.resources.HelloWorldResource
import org.mrfogg.services.AuthHibernateService
import org.mrfogg.services.AuthInMemoryService
import org.mrfogg.widget.WidgetProvider

class MrFoggService extends Service<MrFoggConfiguration> {
    List widgets = []

    static final Class[] ENTITIES = [
        org.mrfogg.domains.User,
        org.mrfogg.domains.Trip
    ]

    public static void main(String[] args) throws Exception {
        new MrFoggService().run(args)
    }

    public MrFoggService() {
        def loader = ServiceLoader.load(WidgetProvider.class)
        loader.each {
            println ">> $it"
            widgets << it
        }
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
        widgets*.initialize(bootstrap)
    }

    @Override
    public void run(MrFoggConfiguration configuration, Environment environment) throws ClassNotFoundException {
        def userDao = new UserDAO(hibernateBundle.sessionFactory)
        def authService = new AuthHibernateService(userDao:userDao)

        final UserDAO userDAO = new UserDAO(hibernateBundle.sessionFactory)
        final TripDAO tripDAO = new TripDAO(hibernateBundle.sessionFactory)
        environment.addResource(new HelloWorldResource(userDAO: userDAO, tripDAO: tripDAO))
        environment.addResource(new AuthResource(authService:authService))
        environment.addResource(new OAuthProvider<User>(new TokenAuthenticator(authService:authService), 'MR.FOGG'))

        // Plugins:
        widgets*.run(configuration, environment)
    }

}
