package org.carl.vert;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;

@Path("/vertx")
public class VertxResource {
    private final Logger log = Logger.getLogger(VertxResource.class);
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

    @Inject
    EventBus bus;

    @GET
    @Path("/hello")
    public Uni<Response> hello() {

        JsonObject message = Json.createObjectBuilder().add("message", "hello from Vert.x").build();
        return Uni.createFrom().item(Response.ok().entity(message).build());
    }


    private static final String URL =
            "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";

    @GET
    @Path("/web")
    public Uni<JsonArray> retrieveDataFromWikipedia() {
        return client
                .getAbs(URL)
                .send()
                .onItem()
                .transform(HttpResponse::bodyAsJsonObject)
                .onItem()
                .transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
    }
}
