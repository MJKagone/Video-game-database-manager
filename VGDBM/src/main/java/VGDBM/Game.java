
/**
 * Represents a game in the game database.
 */

 package VGDBM;

class Game {

    private String game;
    private String score;
    private String year;
    private String note;

    public Game(String game, String score, String year, String note) {
        this.game = game;
        this.score = score;
        this.year = year;
        this.note = note;
    }

    public String getGame() {
        return game;
    }

    public String getScore() {
        return score;
    }

    public String getYear() {
        return year;
    }

    public String getNote() {
        return note;
    }
}