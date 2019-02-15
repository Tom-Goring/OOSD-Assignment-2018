package DB;// kill me what am i doing with my life REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

// TODO: finish Player class
// TODO: make better formatting for toString()
public class Player {

    private String playerName;
    private Team team;

    public Player(String name, Team team) {

        this.playerName = name;
        this.team = team;
        team.addPlayer(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTeam(Team team) { this.team = team; }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        if (this.team != null)
        return team.getTeamName();
        return "None";
    }

    public String toString() { return "Player Name: " + this.playerName + " Team: " + team.getTeamName(); }
}