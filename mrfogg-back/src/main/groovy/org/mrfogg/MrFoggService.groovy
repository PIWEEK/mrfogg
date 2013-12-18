package org.mrfogg

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.auth.oauth.OAuthProvider

import org.mrfogg.auth.TokenAuthenticator
import org.mrfogg.daos.UserDAO
import org.mrfogg.daos.TripDAO
import org.mrfogg.domains.User
import org.mrfogg.resources.TripResource
import org.mrfogg.resources.AuthResource
import org.mrfogg.resources.UserResource
import org.mrfogg.services.AuthHibernateService
import org.mrfogg.widget.WidgetProvider
import org.mrfogg.filter.CorsFilter

import groovy.util.logging.Log4j

@Log4j
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
        def loader = ServiceLoader.load(WidgetProvider)
        loader.each {
            log.debug ">> $it"
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

        final UserDAO userDAO = new UserDAO(hibernateBundle.sessionFactory)
        final TripDAO tripDAO = new TripDAO(hibernateBundle.sessionFactory)
        final AuthHibernateService authService = new AuthHibernateService(userDao:userDAO)

        environment.addFilter(new CorsFilter(), '/*')
        environment.addResource(new UserResource(userDAO: userDAO))
        environment.addResource(new TripResource(tripDAO: tripDAO))
        environment.addResource(new AuthResource(authService:authService))
        environment.addResource(new OAuthProvider<User>(new TokenAuthenticator(authService:authService), 'MR.FOGG'))

        // Plugins:
        widgets*.run(configuration, environment)
    }
}
