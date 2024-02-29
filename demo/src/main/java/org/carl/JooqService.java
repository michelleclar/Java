package org.carl;

import java.nio.charset.StandardCharsets;
import io.quarkus.reactive.datasource.ReactiveDataSource;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/jooq")
public class JooqService {

  @Inject
  @ReactiveDataSource("pg")
  PgPool client;

  @GET
  @Path("/lorem")
  public Uni<String> readShortFile() {

    return vertx
        .fileSystem()
        .readFile("lorem.txt")
        .onItem()
        .transform(content -> content.toString(StandardCharsets.UTF_8));
  }
}
