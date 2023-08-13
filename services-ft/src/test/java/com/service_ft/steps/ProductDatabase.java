package com.service_ft.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

public class ProductDatabase {


    public record Product(Integer id, String name, Integer calories) {
    }

    public static final String PRODUCT_DB_USER = "product-db-user";
    public static final String PRODUCT_DB_PASSWORD = "product-db-password";

    public static final String JDBC_URL = "jdbc:mysql://localhost:3307/product-db";

    private final static String INSERT_QUERY = "INSERT INTO products (id, name, calories) values (?, ?, ?)";

    private ScenarioContext scenarioContext;

    public ProductDatabase(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public <R> R productDBStatement(String query, Function<ResultSet, R> map) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, PRODUCT_DB_USER, PRODUCT_DB_PASSWORD);

        //Create Statement Object
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // While Loop to iterate through all data and print results
        R result = map.apply(rs);

        con.close();

        return result;
    }

    public boolean productDBQuery(String query) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, PRODUCT_DB_USER, PRODUCT_DB_PASSWORD);

        //Create Statement Object
        Statement stmt = con.createStatement();
        boolean execute = stmt.execute(query);

        // While Loop to iterate through all data and print results

        con.close();
        return execute;
    }

    public boolean productDBInsertPrepared(String query, Consumer<PreparedStatement> preparedStatementConsumer) throws Exception {
        Connection con = DriverManager.getConnection(JDBC_URL, PRODUCT_DB_USER, PRODUCT_DB_PASSWORD);

        //Create Statement Object
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatementConsumer.accept(preparedStatement);
        boolean execute = preparedStatement.execute();

        // While Loop to iterate through all data and print results

        con.close();
        return execute;
    }

    @Given("^product id (\\d+) does not exist$")
    public void productIdDoesNotExist(int productId) throws Exception {
        productDBQuery("DELETE FROM products WHERE id = '" + productId + "'");
        Integer integer = productDBStatement(
                "SELECT COUNT(*) FROM products WHERE id = '" + productId + "'",
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

    @Given("product table is empty")
    public void productTableIsEmpty() throws Exception {
        List<Long> listOfIds = productDBStatement(
                "SELECT id FROM products",
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
        Integer integer = productDBStatement(
                "SELECT COUNT(*) FROM products",
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
        productDBQuery("DELETE FROM products WHERE id = " + id);
    }

    @Given("product table has products")
    public void productTableHasProducts(DataTable dataTable) throws Exception {
        productTableIsEmpty();
        List<List<String>> lists = dataTable.asLists();
        for (List<String> list : lists) {
            final long id = Long.parseLong(list.get(0));
            final String name = list.get(1);
            final int calories = Integer.parseInt(list.get(2));
            productDBInsertPrepared(INSERT_QUERY, preparedStatement -> {
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
