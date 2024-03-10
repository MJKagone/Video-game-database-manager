package VGDBM;

/**
 * Represents a game in the game database.
 */
class Game {

    private String game;
    private String score;
    private String year;
    private String platform;
    private String notes;

    public Game(String game, String score, String year, String platform, String notes) {
        this.game = game;
        this.score = score;
        this.year = year;
        this.platform = platform;
        this.notes = notes;
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

    public String getPlatform() {
        return platform;
    }

    public String getNotes() {
        return notes;
    }
}