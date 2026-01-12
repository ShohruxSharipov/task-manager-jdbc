package model;

public class User {
    public int id;
    public String name;
    public String username;
    public String password;
    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    User( String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
