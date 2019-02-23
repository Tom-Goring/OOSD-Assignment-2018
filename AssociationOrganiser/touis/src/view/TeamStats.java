package view;

public class TeamStats {

    private String TeamName;
    private String matchesPlayed;
    private String matchesWon;
    private String setsWon;
    private String gamesWon;

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(String matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public String getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(String matchesWon) {
        this.matchesWon = matchesWon;
    }

    public String getSetsWon() {
        return setsWon;
    }

    public void setSetsWon(String setsWon) {
        this.setsWon = setsWon;
    }

    public String getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(String gamesWon) {
        this.gamesWon = gamesWon;
    }
}
