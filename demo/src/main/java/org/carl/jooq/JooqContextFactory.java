package org.carl.jooq;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
@ApplicationScoped
public class JooqContextFactory {


  @Inject
  AgroalDataSource defaultDataSource;

  @Inject
  @DataSource("pg")
  AgroalDataSource pgDataSource;

  @Inject
  @DataSource("mariadb")
  AgroalDataSource mariadbDataSource;

  public JooqContext createJooqContext(RequestContext requestContext) {
    return createJooqContext(requestContext,SQLDialect.MYSQL);
  }

  public JooqContext createJooqContext(RequestContext requestContext, SQLDialect sqlDialect) {
    try {
      Configuration configuration = getConfiguration(requestContext);
      switch (sqlDialect){
        case MYSQL -> configuration.set(defaultDataSource);
        case POSTGRES -> configuration.set(pgDataSource);
        case MARIADB -> configuration.set(mariadbDataSource);
      }
      DSLContext ctx = DSL.using(configuration);
      return new JooqContext(requestContext, ctx);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Configuration getConfiguration(RequestContext requestContext) {
    Configuration configuration =
        new DefaultConfiguration()
//            .set(defaultDataSource)
            .set(
                new Settings()
                    .withExecuteLogging(true)
                    .withRenderFormatted(true)
                    .withRenderCatalog(false)
                    .withRenderSchema(false)
                    .withMaxRows(Integer.MAX_VALUE)
                    .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
                    .withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED)
                    .withExecuteLogging(true));
    configuration.set(new DefaultRecordListenerProvider(new JooqInsertListener()));
    configuration.set(new DefaultExecuteListenerProvider(new JooqExecuteListener(requestContext)));
    return configuration;
  }
}
