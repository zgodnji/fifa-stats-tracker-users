package com.zgodnji.fifastattracker;

import com.kumuluz.ee.discovery.annotations.DiscoverService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RequestScoped
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

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

    @GET
    @Path("create")
    public Response fillDatabse() {
        Database.addUser(new User(
                "1",
                "Jakob",
                "Kolencar",
                new String[]{"1", "3"}
        ));
        Database.addUser(new User(
                "2",
                "Andraz",
                "Fifameister",
                new String[]{"2"}
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

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private URL url;

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private String urlString;

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private WebTarget webTarget;

    @GET
    @Path("games")
    public Response getGamesAllUsers() {
        try {
            URL url = new URL(urlString + "/v1/games");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("games-discover")
    public Response discoverGames() {
        String response = "{" +
                "\"Url\":\"" + url.toString() + "\"," +
                "\"urlString\":\"" + urlString.toString() + "\"," +
                "\"WebTarget\":\"" + webTarget.toString() + "\"" +
                "}";

        response = String.format(
                response,
                properties.getStringProperty(),
                properties.getBooleanProperty(),
                properties.getIntegerProperty());

        return Response.ok(response).build();
    }

    // Resource for Mejnik 1
    @GET
    @Path("info")
    public Response mejnik01() {
        String response = "{\"clani\": [\"ak6688\", \"js4466\"],\n" +
                "    \"opis_projekta\": \"Nas projekt implementira aplikacijo za sledenje rezultatov medsebojnih tekem v igrah serije FIFA.\",\n" +
                "    \"mikrostoritve\": [\"http://169.51.27.89:31366/v1/users\", \"http://169.51.27.89:31751/v1/games\"],\n" +
                "    \"github\": [\"https://github.com/zgodnji/fifa-stats-tracker-users\", \"https://github.com/zgodnji/fifa-stats-tracker-games\"],\n" +
                "    \"travis\": [\"https://travis-ci.org/zgodnji/fifa-stats-tracker-users\", \"https://travis-ci.org/zgodnji/fifa-stats-tracker-games\"],\n" +
                "    \"dockerhub\": [\"https://hub.docker.com/r/jojo27/fifa-stats-tracker-users/\", \"https://hub.docker.com/r/jojo27/fifa-stats-tracker-games/\"]}";


        return Response.ok(response).build();
    }

}
