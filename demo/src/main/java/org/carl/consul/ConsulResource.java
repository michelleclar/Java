package org.carl.consul;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/greeting")
public class ConsulResource {

    @ConfigProperty(name = "greeting.message", defaultValue="Hello Consul")
    String message;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return message;
    }
}
