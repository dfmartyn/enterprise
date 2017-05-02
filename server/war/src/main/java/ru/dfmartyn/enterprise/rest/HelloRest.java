package ru.dfmartyn.enterprise.rest;


import ru.dfmartyn.enterprise.dto.ValueText;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface HelloRest extends Serializable {

    @GET
    @Path("/say")
    String sayHello();

    @GET
    @Path("/get")
    ValueText getValueText();

    @POST
    @Path("/set")
    Response setValueText(@Valid ValueText valueText);
}
