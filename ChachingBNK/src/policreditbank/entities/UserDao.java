/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package policreditbank.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import policreditbank.connection.ConnectionUtil;

/**
 *
 * @author mihai
 */
public class UserDao {

    private static final String insertUserStatment = "insert into user values(default, ?, ?, ?, ?, ?, ?)";
    private static final String selectUsernameStatment = "select * from user where username = ?";
    private static final String getUserUsingCredentialsStatment = "select * from user where username = ? and password = ?";

    public static boolean saveUser(User user) {
        if (isUsernameUsed(user.getUsername())) {
            System.out.println("Username already in use");
            return false;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnectio();
            preparedStatement = connection.prepareStatement(insertUserStatment);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getToken());

            preparedStatement.executeUpdate();
            System.out.println("User created!");

        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
        };

        return true;
    }

    private static boolean isUsernameUsed(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnectio();
            preparedStatement = connection.prepareStatement(selectUsernameStatment);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
        };

        return false;
    }

    public static User getUserUsingCredentials(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionUtil.getConnectio();
            preparedStatement = connection.prepareStatement(getUserUsingCredentialsStatment);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setToken(resultSet.getString("token"));
                user.setId(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
        };
        return user;
    }
}
