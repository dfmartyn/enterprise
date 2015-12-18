package ru.dfmartyn.enterprise.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public interface HelloRest extends Serializable {

    @GET
    @Path("/say")
    String sayHello();
}
