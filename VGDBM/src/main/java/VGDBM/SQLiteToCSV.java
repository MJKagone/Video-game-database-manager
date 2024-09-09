package VGDBM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CSVToSqliteFactory.java
 * Reads in an SQLite database and creates a new CSV file with the same data sorted by date.
 */
class SQLiteToCSV {

    private DatabaseManager db;

    SQLiteToCSV(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Reads an SQLite database and writes the data to a CSV file.
     * 
     * @param address the file path of the CSV file to be written
     * @throws FileNotFoundException if the specified file path is invalid
     */
    public void writeCSV(String address) throws FileNotFoundException {

        File file = new File(address);
        PrintWriter writer = new PrintWriter(file);

        try {

            String game;
            String score;
            String year;
            String platform;
            String notes;
            ResultSet rs = db.fetchAllByDate();
            writer.print("Game,Score,Year,Platform,Notes\n");

            while (rs.next()) {
                
                game = rs.getString("Game");

                if (rs.getString("Score") == null) {
                    score = "-";
                } 
                else {
                    score = rs.getString("Score");
                }

                if (rs.getString("Year") == null) {
                    year = "-";
                } 
                else {
                    year = rs.getString("Year");
                }
                
                platform = rs.getString("Platform");
                notes = rs.getString("Notes");
                writer.print(game + ";" + score + ";" + year + ";" + platform + ";" + notes + "\n");
            }
        }

        catch (SQLException e) {
            System.out.println("SQLException");
        }

        writer.close();
        
    }
}