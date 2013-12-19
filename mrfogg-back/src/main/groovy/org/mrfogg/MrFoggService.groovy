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
import org.mrfogg.daos.BaseDAO
import org.mrfogg.domains.User
import org.mrfogg.resources.TaskResource
import org.mrfogg.resources.TripResource
import org.mrfogg.resources.AuthResource
import org.mrfogg.resources.FixtureResource
import org.mrfogg.resources.UserResource
import org.mrfogg.services.TaskService
import org.mrfogg.services.TripService
import org.mrfogg.services.AuthHibernateService
import org.mrfogg.services.FixtureService
import org.mrfogg.widget.WidgetProvider
import org.mrfogg.filter.CorsFilter

import groovy.util.logging.Log4j
import org.hibernate.SessionFactory

@Log4j
class MrFoggService extends Service<MrFoggConfiguration> {

    List widgets = []

    static final Class[] ENTITIES = [
        org.mrfogg.domains.User,
        org.mrfogg.domains.Trip,
        org.mrfogg.domains.Task,
        org.mrfogg.domains.Card,
        org.mrfogg.domains.Widget
    ]

    static final Class[] DAOS = [
        org.mrfogg.daos.UserDAO,
        org.mrfogg.daos.TripDAO,
        org.mrfogg.daos.TaskDAO,
        org.mrfogg.daos.CardDAO,
        org.mrfogg.daos.WidgetDAO
    ]

    public static void main(String[] args) throws Exception {
        new MrFoggService().run(args)
    }

    public MrFoggService() {
        widgets +=
            ServiceLoader.load(WidgetProvider).
            collect { w ->
                log.debug ">> $w"
                return w
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

        final Map<String,BaseDAO> daoMap = getDaoMap(hibernateBundle.sessionFactory)
        final AuthHibernateService authService = new AuthHibernateService(userDao: daoMap.userDAO)
        final TripService tripService = new TripService(tripDao: daoMap.tripDAO, userDao: daoMap.userDAO)
        final TaskService taskService = new TaskService(taskDao: daoMap.taskDAO)
        final FixtureService fixtureService = [daoMap]

        environment.with {
            addFilter(new CorsFilter(), '/*')
            addResource(new UserResource(userDAO: daoMap.userDAO))
            addResource(new TripResource(tripService: tripService))
            addResource(new TaskResource(taskService: taskService))
            addResource(new AuthResource(authService: authService))
            addResource(new FixtureResource(fixtureService: fixtureService))
            addResource(new OAuthProvider<User>(new TokenAuthenticator(authService:authService), 'MR.FOGG'))
        }

        // Plugins:
        widgets*.run(configuration, environment)
    }

    Map<String, BaseDAO> getDaoMap(SessionFactory sessionFactory) {

        return DAOS.collectEntries { daoClazz ->
            def key = daoClazz.simpleName.replaceAll(/^./) { it.toLowerCase() }
            def value = daoClazz.newInstance(sessionFactory)

            [(key.toString()) : value]
        }

    }
}
