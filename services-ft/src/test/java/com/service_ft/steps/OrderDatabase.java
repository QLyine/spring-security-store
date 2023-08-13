package com.service_ft.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDatabase {


    public record Order(Long id, Long userId, List<Long> productId) {
    }

    public static final String ORDER_DB_USER = "order-db-order";
    public static final String ORDER_DB_PASSWORD = "order-db-password";

    public static final String JDBC_URL = "jdbc:mysql://localhost:3308/order-db";

    private final static String INSERT_QUERY = "INSERT INTO orders (id, user_id, product_id) values (?, ?, ?)";

    private ScenarioContext scenarioContext;

    public OrderDatabase(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public <R> R orderDBStatement(String query, Function<ResultSet, R> map) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, ORDER_DB_USER, ORDER_DB_PASSWORD);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        R result = map.apply(rs);

        con.close();

        return result;
    }

    public boolean orderDBQuery(String query) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, ORDER_DB_USER, ORDER_DB_PASSWORD);

        Statement stmt = con.createStatement();
        boolean execute = stmt.execute(query);


        con.close();
        return execute;
    }

    public boolean orderDBInsertPrepared(String query, Consumer<PreparedStatement> preparedStatementConsumer) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, ORDER_DB_USER, ORDER_DB_PASSWORD);

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatementConsumer.accept(preparedStatement);
        boolean execute = preparedStatement.execute();

        con.close();
        return execute;
    }

    @Given("^order id (\\d+) does not exist$")
    public void orderIdDoesNotExist(int orderId) throws Exception {
        orderDBQuery("DELETE FROM orders WHERE id = '" + orderId + "'");
        Integer integer = orderDBStatement(
                "SELECT COUNT(*) FROM orders WHERE id = '" + orderId + "'",
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

    @Given("order table is empty")
    public void orderTableIsEmpty() throws Exception {
        List<Long> listOfIds = orderDBStatement(
                "SELECT id FROM orders",
                resultSet -> {
                    try {
                        List<Long> ids = new LinkedList<>();
                        while (resultSet.next()) {
                            ids.add(resultSet.getLong(1));
                        }
                        return ids;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        listOfIds.parallelStream()
                .forEach(id -> {
                    try {
                        deleteRecord(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        Integer integer = orderDBStatement(
                "SELECT COUNT(*) FROM orders",
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

    private void deleteRecord(long id) throws Exception {
        orderDBQuery("DELETE FROM orders WHERE id = " + id);
    }

    @Given("order table has orders")
    public void orderTableHasProducts(DataTable dataTable) throws Exception {
        orderTableIsEmpty();
        List<List<String>> lists = dataTable.asLists();
        for (List<String> list : lists) {
            final long id = Long.parseLong(list.get(0));
            final String name = list.get(1);
            final int calories = Integer.parseInt(list.get(2));
            orderDBInsertPrepared(INSERT_QUERY, preparedStatement -> {
                try {
                    preparedStatement.setLong(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setInt(3, calories);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

}
