package com.sda.jdbc;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/jdbc", "root", "Hania127");
        ) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS MOVIES\n" +
                        "(\n" +
                        "id INTEGER AUTO_INCREMENT,\n" +
                        "title VARCHAR(255),\n" +
                        "genre VARCHAR(255),\n" +
                        "yearOfRelease INTEGER,\n" +
                        "PRIMARY KEY (id)\n" +
                        ");");
            }
//            try(Statement statement = connection.createStatement()){
//                final String insertIndinaJones = "insert into movies (title, genre, yearOfRelease)\n" +
//                        "values\n" +
//                        "('Indiana Jones',\n" +
//                        "'Adventure',\n" +
//                        "1996);";
//                final String insertLOTR = "insert into movies (title, genre, yearOfRelease)\n" +
//                        "values\n" +
//                        "('Lord of the ring',\n" +
//                        "'Fantasy',\n" +
//                        "2001);";
//                final String insertIronMan = "insert into movies (title, genre, yearOfRelease)\n" +
//                        "values\n" +
//                        "('Iron Man',\n" +
//                        "'Adventure',\n" +
//                        "2016);";
//                statement.execute(insertIndinaJones);
//                statement.execute(insertLOTR);
//                statement.execute(insertIronMan);
//            }
            String updateQuerry = "UPDATE MOVIES SET title = ?, genre = ? WHERE id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuerry)) {
                preparedStatement.setString(1, "Indiana Jones 2");
                preparedStatement.setString(2, "drama");
                preparedStatement.setInt(3, 11);
                preparedStatement.executeUpdate();
            }
            String deleteQuerry = "DELETE FROM MOVIES WHERE id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuerry)) {
                preparedStatement.setInt(1, 9);
                preparedStatement.execute();
            }
            try (Statement statement = connection.createStatement()){
               String showTableQuerry = "SELECT * FROM MOVIES;";
               ResultSet resultSet = statement.executeQuery(showTableQuerry);
               while (resultSet.next()){
                   System.out.print(resultSet.getInt("id") + ", ");
                   System.out.print(resultSet.getString("title") + ", ");
                   System.out.print(resultSet.getString("genre") + ", ");
                   System.out.print(resultSet.getInt("yearOfRelease"));
                   System.out.println();
               }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
