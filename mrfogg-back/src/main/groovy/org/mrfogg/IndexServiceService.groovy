package org.mrfogg

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.auth.oauth.OAuthProvider

import org.mrfogg.services.AuthInMemoryService
import org.mrfogg.resources.HelloWorldResource
import org.mrfogg.resources.AuthResource
import org.mrfogg.auth.TokenAuthenticator
import org.mrfogg.domains.User

class IndexServiceService extends Service<IndexServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new IndexServiceService().run(args)
    }

    HibernateBundle<IndexServiceConfiguration> hibernateBundle =
        new HibernateBundle<IndexServiceConfiguration>([]) {
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
        def authService = new AuthInMemoryService()

        environment.addResource(new HelloWorldResource())
        environment.addResource(new AuthResource(authService:authService))
        environment.addResource(new OAuthProvider<User>(new TokenAuthenticator(authService:authService), "MR.FOGG"))
    }
}
