package com.service_ft.steps;

import io.cucumber.java.en.Given;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDatabase {
    public record User(String username, String password, String passwordDB, String scopes) {
    }

    ;
    public final static User VALID_USER = new User(
            "valid",
            "valid",
            "$2a$10$7h62ygf8qDuNLcC0M3tej.y3XIkjmNxRF8qpdfMzOzLRgMYb1yiUe",
            "SCOPE_user;SCOPE_product;SCOPE_order"
    );

    private final static String INSERT_QUERY = "INSERT INTO users (id, password, username, calories, scopes, deleted) values (?, ?, ?, ?, ?, ?)";

    private ScenarioContext scenarioContext;

    public UserDatabase(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public <R> R userDBStatement(String query, Function<ResultSet, R> map) throws Exception {
        //Connection URL Syntax: "jdbc:postgresql://ipaddress:portnumber/db_name"
        String dbUrl = "jdbc:mysql://localhost:3306/user-db";

        String username = "user-db-user";

        String password = "user-db-password";

        Connection con = DriverManager.getConnection(dbUrl, username, password);

        //Create Statement Object
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // While Loop to iterate through all data and print results
        R result = map.apply(rs);

        con.close();

        return result;
    }

    public boolean userDBQuery(String query) throws Exception {
        //Connection URL Syntax: "jdbc:postgresql://ipaddress:portnumber/db_name"
        String dbUrl = "jdbc:mysql://localhost:3306/user-db";

        String username = "user-db-user";

        String password = "user-db-password";

        Connection con = DriverManager.getConnection(dbUrl, username, password);

        //Create Statement Object
        Statement stmt = con.createStatement();
        boolean execute = stmt.execute(query);

        // While Loop to iterate through all data and print results

        con.close();
        return execute;
    }

    public boolean userDBInsertPrepared(String query, Consumer<PreparedStatement> preparedStatementConsumer) throws Exception {
        //Connection URL Syntax: "jdbc:postgresql://ipaddress:portnumber/db_name"
        String dbUrl = "jdbc:mysql://localhost:3306/user-db";

        String username = "user-db-user";

        String password = "user-db-password";

        Connection con = DriverManager.getConnection(dbUrl, username, password);

        //Create Statement Object
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatementConsumer.accept(preparedStatement);
        boolean execute = preparedStatement.execute();

        // While Loop to iterate through all data and print results

        con.close();
        return execute;
    }

    @Given("^user (.*) does not exist")
    public void userDoesNotExist(String user) throws Exception {
        userDBQuery("DELETE FROM users WHERE username LIKE '" + user + "'");
        Integer integer = userDBStatement(
                "SELECT COUNT(*) FROM users WHERE username LIKE '" + user + "'",
                resultSet -> {
                    try {
                        resultSet.next();
                        return resultSet.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        assertThat(integer).isEqualTo(0);

    }

    @Given("I have valid user with id 1")
    public void iHaveValidUserWithId1() throws Exception {
        userDBQuery("DELETE FROM users WHERE username LIKE '" + VALID_USER.username + "'");
        userDBInsertPrepared(INSERT_QUERY, preparedStatement -> {
            try {
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, VALID_USER.passwordDB);
                preparedStatement.setString(3, VALID_USER.username);
                preparedStatement.setInt(4, 0);
                preparedStatement.setString(5, VALID_USER.scopes);
                preparedStatement.setBoolean(6, false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
