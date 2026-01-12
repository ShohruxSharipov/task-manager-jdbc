package db;

import model.User;

import java.sql.*;

public class UserDAO {

    public int authenticate(String username, String passwordHash){
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try(Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean addUser(User user){
        String query = "INSERT INTO users(name, username, password) VALUES (?, ?, ?)";
        try(Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.name);
            statement.setString(2, user.username);
            statement.setString(3, user.password);
            if (statement.executeUpdate() == 1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public User getUser(int id){
        String query = "SELECT * FROM users WHERE id = ?";
        try(Connection connection = Database.getConnection(); PreparedStatement st = connection.prepareStatement(query)){
            st.setInt(1,id);
            try(ResultSet set = st.executeQuery()){
                if(set.next()){
                    String name = set.getString("name");
                    String username = set.getString("username");
                    String password = set.getString("password");
                    return new User(id, name, username, password);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
