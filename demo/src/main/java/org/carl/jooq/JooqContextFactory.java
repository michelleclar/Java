package org.carl.jooq;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;

import io.quarkus.agroal.DataSource;
@ApplicationScoped
public class JooqContextFactory {


  @Inject
  AgroalDataSource defaultDataSource;

  @Inject
  @DataSource("users")
  AgroalDataSource usersDataSource;

  @Inject
  @DataSource("inventory")
  AgroalDataSource inventoryDataSource;

  public JooqContext createJooqContext(RequestContext requestContext) {
    try {
      DSLContext ctx = DSL.using(getConfiguration(requestContext));
      return new JooqContext(requestContext, ctx);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Configuration getConfiguration(RequestContext requestContext) {
    Configuration configuration =
        new DefaultConfiguration()
            .set(defaultDataSource)
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
