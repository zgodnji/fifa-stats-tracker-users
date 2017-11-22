package com.zgodnji.fifastattracker;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String[] games;

    public User(String id, String firstName, String lastName, String[] games) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.games = games;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getGames() {return games; }

    public void setGames(String[] games) {this.games = games; }
}
