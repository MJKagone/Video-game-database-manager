/*  
 * CSVToSqliteFactory.java
 * Reads in an SQLite database and creates a new CSV file with the same data sorted by date.
 */

 package VGDBM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

class SQLiteToCSVFactory {

    private DatabaseManager db;

    SQLiteToCSVFactory(DatabaseManager db) {
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
            ResultSet rs = db.fetchAllByDate();
            writer.print("Game,Score,Year,Notes\n");
            while (rs.next()) {
                String game = rs.getString("Game");
                String score = rs.getString("Score");
                String year = rs.getString("Year");
                String notes = rs.getString("Notes");
                writer.print(game + "," + score + "," + year + "," + notes + "\n");
            }
        }

        catch (SQLException e) {
            System.out.println("SQLException");
        }

        writer.close();
        
    }
}