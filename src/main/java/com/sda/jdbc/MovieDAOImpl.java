package com.sda.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {
    private final Connection connection;
    private static MovieDAOImpl instance;

    //Singleton
    public static MovieDAOImpl getInstance(Connection connection) {
        if (instance == null) { // (1)
            instance = new MovieDAOImpl(connection); // (2)
        }
        return instance;
    }

    private MovieDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABL IF NOT EXISTS MOVIES\n" +
                    "(\n" +
                    "id INTEGER AUTO_INCREMENT,\n" +
                    "title VARCHAR(255),\n" +
                    "genre VARCHAR(255),\n" +
                    "yearOfRelease INTEGER,\n" +
                    "PRIMARY KEY (id)\n" +
                    ");");
        } catch (SQLException throwables) {
            throw new DataBaseActionException("Problem with query execution", throwables);
        }
    }

    @Override
    public void deleteTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM MOVIES");
        } catch (SQLException throwables) {
            throw new DataBaseActionException("Problem with table deletion", throwables);
        }
    }

    @Override
    public int createMovie(Movie movie) {
        String query = "INSERT INTO MOVIES (title, genre, yearOfRelease) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setInt(3, movie.getYearOfRelease());
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataBaseActionException("Problem with inserting movie", throwables);
        }
    }

    @Override
    public void deleteMovie(int id) {
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) {
    }

    @Override
    public Optional<Movie> findMovieById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        try (Statement statement = connection.createStatement()) {
            boolean result = statement.execute("SELECT * FROM MOVIES");
            if (result) {
                ResultSet resultSet = statement.getResultSet();
                List<Movie> movieList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    int yearOfRelaease = resultSet.getInt("yearOfRelease");
                    Movie movie = new Movie(id, title, genre, yearOfRelaease);
                    movieList.add(movie);
                }
                return movieList;
            }
            return new ArrayList<>();
        } catch (SQLException throwables) {
            throw new DataBaseActionException("Problem with finding all movies", throwables);
        }
    }
}
