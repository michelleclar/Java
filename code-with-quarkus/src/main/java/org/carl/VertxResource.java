package org.carl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.file.OpenOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.nio.charset.StandardCharsets;

@Path("/vertx")
public class VertxResource {

  private final Vertx vertx;
  private final WebClient client;

  @Inject
  public VertxResource(Vertx vertx) {
    this.vertx = vertx;
    this.client = WebClient.create(vertx);
  }

  @GET
  @Path("/lorem")
  public Uni<String> readShortFile() {
    return vertx
        .fileSystem()
        .readFile("lorem.txt")
        .onItem()
        .transform(content -> content.toString(StandardCharsets.UTF_8));
  }

  @GET
  @Path("/book")
  public Multi<String> readLargeFile() {
    return vertx
        .fileSystem()
        .open("book.txt", new OpenOptions().setRead(true))
        .onItem()
        .transformToMulti(file -> file.toMulti())
        .onItem()
        .transform(content -> content.toString(StandardCharsets.UTF_8) + "\n------------\n");
  }

  @Inject EventBus bus;

  @GET
  @Path("/hello")
  public Uni<String> hello(@QueryParam("name") String name) {
    return bus.<String>request("greetings", name).onItem().transform(response -> response.body());
  }
}
