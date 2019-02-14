package DB;// kill me what am i doing with my life REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

// TODO: finish Player class
// TODO: make better formatting for toString()
public class Player {

    private String playerName;
    private String teamName;

    public Player(String name, String teamName) {

        this.playerName = name;
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}