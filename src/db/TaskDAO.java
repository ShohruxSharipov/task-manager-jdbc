package db;

import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public boolean addTask(Task task) {
        String sql = "INSERT INTO tasks(name,description,owner_id,done) VALUES(?,?,?,?)";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,task.name);
            statement.setString(2,task.description);
            statement.setInt(3,task.owner);
            statement.setBoolean(4,task.done);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Task> getTasks(int id, boolean done) {
        String sql = "SELECT * FROM tasks where owner_id = ? and done = ? order by id asc";
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setBoolean(2, done);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int t_id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Task task = new Task(t_id,name,description,id,done);
                    tasks.add(task);
                }
                return tasks;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasks(int id) {
        String sql = "SELECT * FROM tasks where owner_id = ?";
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int t_id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean done = resultSet.getBoolean("done");
                    Task task = new Task(t_id,name,description,id,done);
                    tasks.add(task);
                }
                return tasks;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean markDone(int id,int user_id) {
        String sql = "UPDATE tasks SET done = ? WHERE id = ? and owner_id = ?";
        try(Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(2, id);
            statement.setInt(3, user_id);
            statement.setBoolean(1, true);
            return statement.executeUpdate() == 1;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
