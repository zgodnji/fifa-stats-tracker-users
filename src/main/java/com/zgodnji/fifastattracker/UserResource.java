package com.zgodnji.fifastattracker;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {

    @Inject
    private UserProperties properties;

    @GET
    @Path("test")
    public Response testResponse() {
        String response =
                "{" +
                        "\"stringProperty\": \"%s\"," +
                        "\"booleanProperty\": %b," +
                        "\"integerProperty\": %d" +
                        "}";

        response = String.format(
                response,
                properties.getStringProperty(),
                properties.getBooleanProperty(),
                properties.getIntegerProperty());

        return Response.ok(response).build();
    }

    @GET
    public Response getAllUsers() {
        List<User> users = Database.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") String userId) {
        User user = Database.getUser(userId);
        return user != null
                ? Response.ok(user).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addNewUser(User user) {
        Database.addUser(user);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") String userId) {
        Database.deleteUser(userId);
        return Response.noContent().build();
    }
}
