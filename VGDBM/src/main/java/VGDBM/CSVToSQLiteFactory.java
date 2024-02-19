
/*  
 * CSVToSqliteFactory.java
 * Reads in a CSV file and creates a new SQLite database with the same data.
 */

package VGDBM;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.SQLException;


class CSVToSQLiteFactory {

    static DatabaseManager db;

    /**
     * Reads a CSV file and inserts the data into a SQLite database.
     * 
     * @param address the file path of the CSV file to be read
     * @throws FileNotFoundException if the specified file path is invalid or the file does not exist
     */
    public void readCSV(String address) throws FileNotFoundException {

        File file = new File(address);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n");

        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] parts = line.split(",");
            String game = parts[0];
            String score = parts[1];
            String year = parts[2];
            String notes = parts[3];
            Game newGame = new Game(game, score, year, notes);
            if (!newGame.getGame().equals("Game")) {
                try {
                    db.insertGame(newGame);
                }
                catch (SQLException e) {
                    System.out.println("SQLException");
                }
            }
        }
        scanner.close();
    }



    public static void main(String[] args) {

        db = new DatabaseManager();
        String CSVAddress = "." + "\\games.csv";

        try {
            db.open(".", "importedGames.sqlite");
            db.clearDatabase();
            CSVToSQLiteFactory factory = new CSVToSQLiteFactory();
            factory.readCSV(CSVAddress);
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}