/**
 * This class contains unit tests for the DatabaseManager class.
 */

 package VGDBM;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;


public class DatabaseManagerTest {

    DatabaseManager db;
    String address;
    String fileName;

    @Before
    public void setUp() {
        db = new DatabaseManager();
        address = "." + "\\";
        fileName = "testGames.sqlite";
        try {
            db.open(address, fileName);
            db.clearDatabase();
        } catch (Exception e) {
            fail();
        }
    }

    
    @Test
    public void insertGame() {

        try {
            Game game1 = new Game("Testgame", "TestScore", "TestDate", "TestNote");
            Game game2 = new Game("Testgame2", "TestScore", "2020", "TestNote2");
            Game game3 = new Game("Testgame3", "5", "TestDate", "TestNote3");
            assertThrows(IllegalArgumentException.class, () -> db.insertGame(game1));
            assertThrows(IllegalArgumentException.class, () -> db.insertGame(game2));
            assertThrows(IllegalArgumentException.class, () -> db.insertGame(game3));
            Game game4 = new Game("Testgame4", "5", "2020", "TestNote4");
            Game game5 = new Game("Testgame5", "-", "2021", "TestNote5");
            Game game6 = new Game("Testgame6", "5", "-", "TestNote6");
            Game game7 = new Game("Testgame7", "-", "-", "TestNote7");
            db.insertGame(game4);
            db.insertGame(game5);
            db.insertGame(game6);
            db.insertGame(game7);
            assertTrue(db.gameExists("Testgame4"));
            assertTrue(db.gameExists("Testgame5"));
            assertTrue(db.gameExists("Testgame6"));
            assertTrue(db.gameExists("Testgame7"));
            Game insertedGame4 = db.fetchGame("Testgame4");
            Game insertedGame5 = db.fetchGame("Testgame5");
            Game insertedGame6 = db.fetchGame("Testgame6");
            Game insertedGame7 = db.fetchGame("Testgame7");
            assertTrue(insertedGame4.getGame().equals("Testgame4"));
            assertTrue(insertedGame4.getScore().equals("5"));
            assertTrue(insertedGame4.getYear().equals("2020"));
            assertTrue(insertedGame4.getNote().equals("TestNote4"));
            assertTrue(insertedGame5.getGame().equals("Testgame5"));
            assertTrue(insertedGame5.getScore() == null);
            assertTrue(insertedGame5.getYear().equals("2021"));
            assertTrue(insertedGame5.getNote().equals("TestNote5"));
            assertTrue(insertedGame6.getGame().equals("Testgame6"));
            assertTrue(insertedGame6.getScore().equals("5"));
            assertTrue(insertedGame6.getYear() == null);
            assertTrue(insertedGame6.getNote().equals("TestNote6"));
            assertTrue(insertedGame7.getGame().equals("Testgame7"));
            assertTrue(insertedGame7.getScore() == null);
            assertTrue(insertedGame7.getYear() == null);
            assertTrue(insertedGame7.getNote().equals("TestNote7"));
            
            
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void updateGame() {

        try {
            Game game = new Game("Testgame", "5", "2020", "TestNote");
            db.insertGame(game);
            Game updatedGame = new Game("Updategame", "0", "2021", "TestNote2");
            db.updateGame("Testgame", updatedGame);
            assertTrue(db.gameExists("Updategame"));
            Game insertedGame = db.fetchGame("Updategame");
            assertTrue(insertedGame.getGame().equals("Updategame"));
            assertTrue(insertedGame.getScore().equals("0"));
            assertTrue(insertedGame.getYear().equals("2021"));
            assertTrue(insertedGame.getNote().equals("TestNote2"));
        } catch (Exception e) {
            fail();
            System.out.println("Error running tests" + e.getMessage());
        }
    }

    @Test
    public void removeGame() {

        try {
            Game game = new Game("Deletedgame", "5", "2020", "TestNote");
            db.insertGame(game);
            db.removeGame("Deletedgame");
            assertTrue(!db.gameExists("Deleted"));
        } catch (Exception e) {
            fail();
            System.out.println("Error running tests" + e.getMessage());
        }
    }

    @Test
    public void nonexistingGames() {

        try {
            assertTrue(!db.gameExists("Nonexistinggame"));
            Game notAGame = db.fetchGame("Nonexistinggame");
            assertTrue(notAGame == null);
        } catch (Exception e) {
            fail();
            System.out.println("Error running tests" + e.getMessage());
        }
    }


    @AfterAll
    public void tearDown() {

        try {
            db.clearDatabase();
            db.close();
        }
        catch (SQLException e) {
            fail();
            System.out.println("Error closing database" + e.getMessage());
        }
    }
}
