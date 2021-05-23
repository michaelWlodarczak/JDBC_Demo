package com.sda.jdbc;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOImplTest {
    private static MovieDAO movieDAO; // pola musza byc statyczne
    private static Connection connection;

    @BeforeAll
    static void setup() throws SQLException { // metody 'cyklu zycia' tj. 'before' & 'after' musza byc statyczne
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/jdbc", "root", "Hania127");
        movieDAO = MovieDAOImpl.getInstance(connection);
    }

    @BeforeEach
    void clearTableBeforeUse(){
        movieDAO.deleteTable();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void createTable() {
        //Given
        //When
        movieDAO.createTable();
        //Then
    }

    @Test
    void sholudThrowSQLExceptionWhileCreatingTable() {  //test sprawdza czy bedzie zlapany wyjatek w skladni SQL
        //Given
        //When
        Assertions.assertThrows(DataBaseActionException.class, () -> movieDAO.createTable());
    }

    @Test
    void deleteTable() {
        movieDAO.deleteTable();
    }

    @Test
    void createMovie() {
        //Given
        Movie movie = new Movie("Pan Tadeusz", "Epopeja", 1834);
        //When
        int returned = movieDAO.createMovie(movie);
        //Then
        Assertions.assertEquals(1,returned);
    }

    @Test
    void findAll() {
        //Given
        movieDAO.createMovie(new Movie("Shrek","Animation",2001));
        movieDAO.createMovie(new Movie("Shrek 2","Animation",2005));
        //When
        List<Movie> movieList = movieDAO.findAll();
        //Then
        Assertions.assertNotNull(movieList);
        Assertions.assertEquals(2,movieList.size());
    }
}