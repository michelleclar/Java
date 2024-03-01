package org.carl;

import java.util.List;
import org.carl.jooq.JooqContext;
import org.carl.jooq.JooqContextFactory;
import org.carl.jooq.RequestContext;
import org.carl.model.Fruit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.reactive.datasource.ReactiveDataSource;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/jooq")
public class JooqService {

  @Inject
  @ReactiveDataSource("pg")
  PgPool client;

  @Inject
  @ConfigProperty(name = "app.schema.create", defaultValue = "false")
  boolean schemaCreate;

  @Inject
  @DataSource("pg")
  AgroalDataSource pgDataSource;

  void config(@Observes StartupEvent ev) {
    if (schemaCreate) {
      initdb();
    }
  }

  private void initdb() {
    // TODO
    client.query("DROP TABLE IF EXISTS fruits").execute()
        .flatMap(r -> client
            .query("CREATE TABLE fruits (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
        .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Orange')").execute())
        .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Pear')").execute())
        .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Apple')").execute()).await()
        .indefinitely();
  }

  @GET
  public Multi<Fruit> get() {
    return Fruit.findAll(client);
  }

  @GET
  @Path("select")
  public List<Fruit> select() {
    DSLContext dsl = DSL.using(pgDataSource,SQLDialect.POSTGRES);
    List<Fruit> into = dsl.fetch("SELECT id, name FROM fruits ORDER BY name ASC").into(Fruit.class);
    return into;
  }

}
