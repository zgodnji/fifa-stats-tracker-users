package com.zgodnji.fifastattracker;

import com.kumuluz.ee.discovery.annotations.DiscoverService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    // service discovery
    @Inject
    @DiscoverService(value="games-service", version = "1.0.x", environment = "dev")
    private WebTarget target;


    // just for testing
    @GET
    @Path("url")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUrl() {
        return Response.ok(target.getUri().toString()).build();
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

        // get users games
        //if (user != null) {
        //    getUserGames(user.getGames());
        //}

        return user != null
                ? Response.ok(user).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    private Response getUserGames(String[] games) {
        // get games objects from 'games' microservice

        WebTarget service = target.path("v1/games");
        Response response;

        for (String game:games) {

            try {
                response = service.request().get();
            } catch (ProcessingException e) {
                return Response.status(408).build();
            }

        }

        return Response.ok().build();
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

    @GET
    @Path("create")
    public Response fillDatabse() {
        Database.addUser(new User(
                "1",
                "Jakob",
                "Kolencar",
                new String[] {"1","3"}
        ));
        Database.addUser(new User(
                "2",
                "Andraz",
                "Fifameister",
                new String[] {"2"}
        ));
       return Response.noContent().build();
    }

    @Inject
    private UserProperties properties;

    @GET
    @Path("config")
    public Response getConfig() {
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

}
