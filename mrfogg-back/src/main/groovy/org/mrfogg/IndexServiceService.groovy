package org.mrfogg

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle

import org.mrfogg.domains.User
import org.mrfogg.daos.UserDAO
import org.mrfogg.resources.HelloWorldResource

class IndexServiceService extends Service<IndexServiceConfiguration> {

    static final Class[] ENTITIES = [
        org.mrfogg.domains.User
    ]

    public static void main(String[] args) throws Exception {
        new IndexServiceService().run(args)
    }

    HibernateBundle<IndexServiceConfiguration> hibernateBundle =

        new HibernateBundle<IndexServiceConfiguration>(ENTITIES) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(IndexServiceConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    MigrationsBundle<IndexServiceConfiguration> migrationsBundle =
        new MigrationsBundle<IndexServiceConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(IndexServiceConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<IndexServiceConfiguration> bootstrap) {
        bootstrap.with {
            name = 'IndexService'
            addBundle migrationsBundle
            addBundle hibernateBundle
        }
    }

    @Override
    public void run(IndexServiceConfiguration configuration, Environment environment) throws ClassNotFoundException {

        final UserDAO userDAO = new UserDAO(hibernateBundle.sessionFactory)


        environment.addResource(new HelloWorldResource(userDAO))
    }
}
