package model;

public class TeamStats {

    public static class Builder {

        private Team team;
        private String matchesPlayed;
        private String matchesWon;
        private String setsWon;
        private String gamesWon;

        public Builder(Team team) {

            this.team = team;
        }

        public Builder withMatchesPlayed(String matchesPlayed) {

            this.matchesPlayed = matchesPlayed;
            return this;
        }

        public Builder withMatchesWon(String matchesWon) {

            this.matchesWon = matchesWon;
            return this;
        }

        public Builder withSetsWon(String setsWon) {

            this.setsWon = setsWon;
            return this;
        }

        public Builder withGamesWon(String gamesWon) {

            this.gamesWon = gamesWon;
            return this;
        }

       public TeamStats build() {

           return new TeamStats(this.team, this.matchesPlayed, this.matchesWon, this.setsWon, this.gamesWon);
       }
    }

    private TeamStats(Team team, String matchesPlayed, String matchesWon, String setsWon, String gamesWon) {

        this.team = team;
        this.matchesPlayed = matchesPlayed;
        this.matchesWon = matchesWon;
        this.setsWon = setsWon;
        this.gamesWon = gamesWon;
    }

    final private Team team;
    final private String matchesPlayed;
    final private String matchesWon;
    final private String setsWon;
    final private String gamesWon;

    // these are not unused - they are used by the TableViews
    public String getTeamName() {
        return team.getTeamName();
    }

    public String getMatchesPlayed() {
        return matchesPlayed;
    }

    public String getMatchesWon() {
        return matchesWon;
    }

    public String getSetsWon() {
        return setsWon;
    }

    public String getGamesWon() {
        return gamesWon;
    }
}
