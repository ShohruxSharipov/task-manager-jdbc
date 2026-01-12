package model;

public class Task {
    public int id;
    public String name;
    public String description;
    public int owner;
    public boolean done;

    public Task(int id, String name, String description, int owner, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.done = done;
    }
}
