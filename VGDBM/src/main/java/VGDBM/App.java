/**
* The main class that represents the application.
* This class contains the main method that serves as the entry point for the application.
* Provides a command-line interface for managing a game database (with some bugs...).
*/
package VGDBM;
import java.sql.SQLException;
import java.util.Scanner;

public class App { 

    public static void main(String[] args) {
        
        DatabaseManager db = new DatabaseManager();
        String address = "." + "\\";
        String fileName = "games.sqlite";

        try {
            db.open(address, fileName);
        } 
        
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            
            System.out.println("The supported operations are:"
            + "\n[1] Add a new game"
            + "\n[2] Update game details"
            + "\n[3] Remove a game"
            + "\n[4] Save database to CSV file"
            + "\n[5] Close the database"
            + "\nWhat would you like to do? ");
        
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("Enter information in the format \"Game,Score,Year,Note\"");
                    String input = scanner.nextLine();
                    
                    try {
                        String[] parts = input.split(",");
                        if (parts.length != 4) {
                            System.out.println("Invalid input. Please enter information in the correct format.");
                            break;
                        }
                        String name = parts[0];
                        String score = parts[1];
                        String year = parts[2];
                        String note = parts[3];
                        Game game = new Game(name, score, year, note);
                        db.insertGame(game);
                        break;
                    }

                    catch (IllegalArgumentException e) {
                        System.out.println("Invalid input. Please enter information in the correct format.");
                        break;
                    }

                    catch (SQLException e) {
                        System.out.println("SQLException");
                        break;
                    }

                case 2:

                    try {

                        System.out.println("Enter the name of the game you want to update");
                        String gameName = scanner.nextLine();
                        if (!db.gameExists(gameName)) {
                            System.out.println("Game not found");
                            System.out.println(db.fetchGame(gameName).getGame());
                            break;
                        }
                    
                        System.out.println("Enter the new name. Press enter to leave unchanged.");
                        String newGame = scanner.nextLine();
                        if (newGame.trim().equals("")) {
                            newGame = gameName;
                        }
                    
                        System.out.println("Enter the new score. Press enter to leave unchanged.");
                        String oldScore = db.fetchGame(gameName).getScore();
                        String newScore = scanner.nextLine();
                        if (newScore.trim().equals("") && oldScore == null) {
                            newScore = "-";
                        }
                        else if (newScore.trim().equals("")) {
                            newScore = oldScore;
                        }

                        System.out.println("Enter the new year. Press enter to leave unchanged.");
                        String oldYear = db.fetchGame(gameName).getYear();
                        String newYear = scanner.nextLine();
                        if (newYear.trim().equals("") && oldYear == null) {
                            newYear = "-";
                        }
                        else if (newYear.trim().equals("")) {
                            newYear = oldYear;
                        }
                    
                        System.out.println("Enter the new note. Press enter to leave unchanged.");
                        String newnote = scanner.nextLine();
                        if (newnote.trim().equals("")) {
                            newnote = db.fetchGame(gameName).getNote();
                        }
                    
                        db.updateGame(gameName, new Game(newGame, newScore, newYear, newnote));
                        break;

                    } 

                    catch (Exception e) {
                        System.out.println("Invalid input. Please enter information in the correct format.");
                    }

                case 3:

                    System.out.println("Enter the name of the game you want to remove");
                    String gameToRemove = scanner.next();
                    try {
                        db.removeGame(gameToRemove);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    
                    try {
                        SQLiteToCSVFactory factory = new SQLiteToCSVFactory(db);
                        factory.writeCSV(address + "games.csv");
                        System.out.println("Database exported to games.csv");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:

                    try {
                        db.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Database closed.");
                    System.exit(0);

                default:

                    System.out.println("Invalid choice");
            
            scanner.close();

            }
        }
    }
}
